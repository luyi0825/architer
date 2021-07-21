自定义的配置属性都用customize，然后是配置的业务，最后是具体的配置,例如

~~~~
@ConfigurationProperties(prefix = "customize.thread-pool", ignoreInvalidFields = true)
public class TaskExecutorProperties {
/**
* key为线程池的标识，value为线程池配置信息
*/
private Map<String, ThreadPoolConfig> configs;

    public Map<String, ThreadPoolConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, ThreadPoolConfig> configs) {
        this.configs = configs;
    }
}
~~~~