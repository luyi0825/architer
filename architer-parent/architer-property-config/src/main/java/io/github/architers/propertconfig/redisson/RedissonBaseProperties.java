package io.github.architers.propertconfig.redisson;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.InputStream;
import java.io.Serializable;


/**
 * redisson的属性配置
 * <li>file和config二选一，两个同时存在优先file</li>
 *
 * @author luyi
 */
public class RedissonBaseProperties implements Serializable {

    /**
     * 是否开启
     */
    protected boolean enabled = true;
    /**
     * 配置的文件(项目下resources下的路径)
     */
    protected String resourceFile;

    /**
     * 根据属性配置，可以直接配置在yml和properties文件中
     */
    protected Config config;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getResourceFile() {
        return resourceFile;
    }

    public void setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }


    public RedissonClient createClient() {
        try {
            String resourceFile = this.getResourceFile();
            if (resourceFile != null && !resourceFile.isEmpty()) {
                try (InputStream inputStream = this.getConfigStream()) {
                    config = Config.fromYAML(inputStream);
                }
            } else if (this.getConfig() != null) {
                config = this.getConfig();
            } else {
                //默认连接本地
                config = new Config();
                config.useSingleServer()
                        .setAddress("redis://127.0.0.1:6379");
            }
            if (config.getCodec() == null) {
                config.setCodec(new JsonJacksonCodec());
            }
            return Redisson.create(config);
        } catch (Exception e) {
            throw new RuntimeException("create redissonClient failed", e);
        }

    }

    private InputStream getConfigStream() {
        return this.getClass().getClassLoader().getResourceAsStream(resourceFile);
    }

}
