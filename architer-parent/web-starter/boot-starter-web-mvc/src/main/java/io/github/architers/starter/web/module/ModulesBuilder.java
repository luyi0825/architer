package io.github.architers.starter.web.module;


import io.github.architers.context.module.SubModule;
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
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SystemPropertyUtils;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author luyi
 * 构建项目的moule->扫描包，得到每个模块的模块配置类
 * 可以通过set方法修改默认的scanPackages和resourcePattern
 */
public class ModulesBuilder {

    private static final Logger log = LoggerFactory.getLogger(ModulesBuilder.class);

    /**
     * 扫描的包
     */
    private String[] scanPackages = new String[]{"com.core.**", "com.business.**"};

    /**
     * 资源模式
     */
    private String resourcePattern = "*Module.class";

    public ModulesBuilder() {
    }


    /**
     * @param sources 启动配置类
     * @return 所有的启动配置类
     */
    public Class<?>[] buildMoules(Class<?>... sources) {
        Set<Class<?>> moduleClass = new HashSet<>();
        loadModuleClass(getScanPackages(), moduleClass);
        if (ArrayUtils.isNotEmpty(sources)) {
            moduleClass.addAll(Arrays.asList(sources));
        }
        return moduleClass.toArray(new Class[0]);
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
     * @param moduleClass  扫描的类-对应class
     */
    private void loadModuleClass(String[] scanPackages, Set<Class<?>> moduleClass) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        for (String basePackage : scanPackages) {
            if (StringUtils.isEmpty(basePackage)) {
                continue;
            }
            //构建路径位置模式
            String locationPattern = this.buildLocationPattern(basePackage);
            Set<Class<?>> resources = this.parseResourcePattern(locationPattern, metadataReaderFactory, resourcePatternResolver);
            if (!CollectionUtils.isEmpty(resources)) {
                moduleClass.addAll(resources);
            }
        }
    }

    /**
     * 解析resourcePattern
     *
     * @param locationPattern         路径模式
     * @param metadataReaderFactory   元数据读取工厂
     * @param resourcePatternResolver 资源模式解析器
     */
    private Set<Class<?>> parseResourcePattern(String locationPattern, MetadataReaderFactory metadataReaderFactory, ResourcePatternResolver resourcePatternResolver) {
        Set<Class<?>> resourceSet;
        try {
            Resource[] resources = resourcePatternResolver.getResources(locationPattern);
            if (ArrayUtils.isEmpty(resources)) {
                //没有直接返回
                return null;
            }
            resourceSet = new HashSet<>(resources.length);
            for (Resource resource : resources) {
                //检查resource，这里的resource都是class
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (metadataReader != null) {
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
                    SubModule subModule = clazz.getAnnotation(SubModule.class);
                    if (subModule != null) {
                        resourceSet.add(clazz);
                        log.info("start load module:{}-{}-{}", subModule.name(), subModule.caption(), className);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("扫描启动类失败", e);
        }
        return resourceSet;
    }

    /**
     * 构建路径位置模式
     */
    private String buildLocationPattern(String basePackage) {
        return ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) +
                "/" +
                getResourcePattern();
    }


    /**
     * 设置包扫描范围
     *
     * @param scanPackages 包扫描范围
     * @return 模块构建器
     */
    public ModulesBuilder setScanPackages(String[] scanPackages) {
        Assert.notEmpty(scanPackages, "scanPackages is null");
        this.scanPackages = scanPackages;
        return this;
    }

    /**
     * 设置资源形式，一般为class，默认为：*Module.class
     *
     * @param resourcePattern 资源形式
     * @return 模块构建器
     */
    public ModulesBuilder setResourcePattern(String resourcePattern) {
        Assert.notNull(resourcePattern, "resourcePattern not null");
        this.resourcePattern = resourcePattern;
        return this;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public String getResourcePattern() {
        return resourcePattern;
    }
}
