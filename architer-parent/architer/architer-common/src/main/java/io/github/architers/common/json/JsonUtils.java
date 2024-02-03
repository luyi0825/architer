package io.github.architers.common.json;


import lombok.extern.slf4j.Slf4j;


import java.util.ServiceLoader;


/**
 * Json工具类
 * <p>
 * 创建这个类的目的是:
 * 1.调用这无需关心Json序列化的api是什么，如果更换序列化工具，无需更改大量的序列化
 * 2.如果需要更改序列化的方式，直接利用jdk的spi配置即可,无需改动太多代码
 * </p>
 *
 * @author luyi
 * @since 1.0.3
 */
@Slf4j
public final class JsonUtils {
    private static ArchiterJson architerJson;

    /**
     * 防止被new
     */
    private JsonUtils() {


    }

    static {
        ServiceLoader<ArchiterJson> architerJsonServiceLoader = ServiceLoader.load(ArchiterJson.class);
        int count = (int) architerJsonServiceLoader.stream().count();
        if (count > 1) {
            throw new RuntimeException("存在多个ArchiterJson");
        } else if (count == 1) {
            //存在一个就用指定的
            architerJson = architerJsonServiceLoader.findFirst().get();
        } else {
            if (architerJson == null) {
                try {
                    //fastjson2
                    Class.forName("com.alibaba.fastjson2.JSON");
                    architerJson = FastJson2.getInstance();
                } catch (Exception ignored) {
                }
            }
            //Jackson
            try {
                Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
                architerJson = Jackson.getInstance();
            } catch (Exception ignored) {
            }
        }
        if (architerJson == null) {
            throw new RuntimeException("请配置json序列化方式");
        }
        log.info("使用的json序列化方式:" + architerJson.getClass().getName());
    }

    /**
     * Object转成Json字符串
     *
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return architerJson.toJsonString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJsonBytes(Object object) {
        return architerJson.toJsonBytes(object);
    }

    /**
     * 将json转为对象
     *
     * @param bytes json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        return architerJson.parseObject(bytes, clazz);
    }


    /**
     * 将json转为对象
     *
     * @param jsonStr json字符串
     * @param clazz   反序列化的class
     * @param <T>     实体类型
     * @return 反序列化的实体
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return architerJson.parseObject(jsonStr, clazz);
    }

    public static <T> T parseObject(String jsonStr, ArchiterTypeReference<T> typeReference){
        return architerJson.parseObjectByType(jsonStr, typeReference);
    }



}
