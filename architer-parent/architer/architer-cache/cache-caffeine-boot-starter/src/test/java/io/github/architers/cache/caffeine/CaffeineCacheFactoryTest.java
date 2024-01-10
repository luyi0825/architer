//package io.github.architers.cache.caffeine;
//
//
//import com.github.benmanes.caffeine.cache.Cache;
//
//import java.util.concurrent.TimeUnit;
//
//class CaffeineCacheFactoryTest {
//
//
//    public static void main(String[] args) throws InterruptedException {
//        String key = "1";
//        CaffeineCacheFactory caffeineCacheFactory = new CaffeineCacheFactory();
//        Cache<String, Object> cache = caffeineCacheFactory.get("test", 6, TimeUnit.SECONDS);
//        cache.put(key, 666);
//        for(int i=0;i<1000;i++){
//            cache.put("test"+i,i);
//        }
//        System.out.println(cache.getIfPresent(key));
//        Thread.sleep(1000 * 10);
//        System.out.println(cache.estimatedSize());
//        System.out.println(cache.getIfPresent(key));
//        System.out.println("end");
//    }
//}