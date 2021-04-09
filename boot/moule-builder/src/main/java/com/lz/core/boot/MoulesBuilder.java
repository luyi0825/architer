package com.lz.core.boot;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author luyi
 * @date 2021/1/8
 * 构建项目的moule->扫描包，得到每个模块的模块配置类
 */
public class MoulesBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoulesBuilder.class);

    /**
     * 扫描的包
     */
    private final static String[] SCAN_PACKAGES = new String[]{"com.ly.**"};

    /**
     * 后缀，只扫描class
     */
    private final static String DEFAULT_RESOURCE_PATTERN = "*Module.class";


    /**
     * @param sources 启动配置类
     * @return 所有的启动配置类
     */
    public Class<?>[] buildMoules(Class<?>... sources) {
        Set<Class<?>> moduleClass = new HashSet<>();
        loadModuleClass(SCAN_PACKAGES, moduleClass, null);
        if (ArrayUtils.isNotEmpty(sources)) {
            moduleClass.addAll(Arrays.asList(sources));
        }
        return moduleClass.toArray(new Class[0]);
    }

    /**
     * @param sources 启动配置类
     * @return 所有的启动配置类
     */

    public String[] buildMoules(String... sources) {
        Set<String> moduleClass = new HashSet<>();
        if (ArrayUtils.isNotEmpty(sources)) {
            moduleClass.addAll(Arrays.asList(sources));
        }
        loadModuleClass(SCAN_PACKAGES, null, moduleClass);
        return moduleClass.toArray(new String[0]);
    }

    /**
     * 根据扫描包的配置
     * 加载需要检查的方法
     * <p>参考
     * *1.ClassPathBeanDefinitionScanner#doScan
     * *2、ClassPathScanningCandidateComponentProvider#findCandidateComponents
     * 写的扫描的模块类
     * </p>
     *
     * @param scanPackages 扫描的包
     * @param moduleClass1 扫描的类-对应class
     * @param moduleClass2 扫描的类-对应String
     */
    private void loadModuleClass(String[] scanPackages, Set<Class<?>> moduleClass1, Set<String> moduleClass2) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        for (String basePackage : scanPackages) {
            if (StringUtils.isBlank(basePackage)) {
                continue;
            }
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
            try {
                Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    //检查resource，这里的resource都是class
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    if (metadataReader != null) {
                        String className = metadataReader.getClassMetadata().getClassName();
                        Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
                        if (moduleClass1 != null) {
                            moduleClass1.add(clazz);
                        }
                        if (moduleClass2 != null) {
                            moduleClass2.add(className);
                        }
                        LOGGER.info("加载模块："+className);
                    }
                }
            } catch (Exception e) {
                //转换异常
                throw new RuntimeException("扫描启动类失败",e);
            }
        }
    }
}
