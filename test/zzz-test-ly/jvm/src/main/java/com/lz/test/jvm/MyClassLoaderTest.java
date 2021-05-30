package com.lz.test.jvm;

import sun.misc.PerfCounter;
import sun.misc.Resource;
import sun.misc.URLClassPath;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author luyi
 * 自定义类加载器
 */
public class MyClassLoaderTest {
    static class MyClassLoader extends ClassLoader {
        private final String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            String path = name.replace('.', '/').concat(".class");
            // name = name.replaceAll("\\.", File.separator);
            FileInputStream fis = new FileInputStream(classPath + File.separator + path);
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            {
                synchronized (getClassLoadingLock(name)) {
                    Class<?> c = findLoadedClass(name);
                    if (c == null) {
                        long t0 = System.nanoTime();
                        if (name.startsWith("com.lz.test.jvm")) {
                            //自己定义的类自己加载
                            c = findClass(name);
                        } else {
                            //其他的还是走原来的逻辑
                            c = this.getParent().loadClass(name);
                        }
                        if (c == null) {
                            long t1 = System.nanoTime();
                            c = findClass(name);
                            PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                            PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                            PerfCounter.getFindClasses().increment();
                        }
                    }
                    if (resolve) {
                        resolveClass(c);
                    }
                    return c;
                }
            }
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        MyClassLoader classLoader1 = new MyClassLoader("/data/home/luyi/workspace/test");
        Class<?> clazz1 = classLoader1.loadClass("com.lz.test.jvm.User");
        Object obj1 = clazz1.newInstance();
        Method method = clazz1.getDeclaredMethod("getMyName", null);
        method.invoke(obj1, null);
        System.out.println(clazz1.getClassLoader().getClass().getName());


        MyClassLoader classLoader2 = new MyClassLoader("/data/home/luyi/workspace/test");
        Class<?> clazz2 = classLoader2.loadClass("com.lz.test.jvm.User");
        Object obj2 = clazz2.newInstance();
        Method method2 = clazz2.getDeclaredMethod("getMyName", null);
        method2.invoke(obj2, null);
        System.out.println(clazz2.getClassLoader().getClass().getName());
    }
}
