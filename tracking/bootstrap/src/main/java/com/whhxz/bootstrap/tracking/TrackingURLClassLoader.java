package com.whhxz.bootstrap.tracking;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * TrackingURLClassLoader
 * Created by xuzhuo on 2018/3/8.
 */
public class TrackingURLClassLoader extends URLClassLoader {
    private final ClassLoader parent;

    private static final String[] TRACKING_PROFILER_CLASS = new String[]{
            "com.whhxz.tracking.profiler",
            "org.slf4j"

    };

    public TrackingURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.parent = parent;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        /*
         * 1 未被加载时判断是否需要本classLoader加载
         * 如果不需要本类加载由父类加载，父类加载失败时尝试本类加载
         */
        if (clazz == null) {
            if (onLoadClass(name)) {
                clazz = findClass(name);
            } else {
                try {
                    clazz = parent.loadClass(name);
                } catch (ClassNotFoundException e) {
                    clazz = findClass(name);
                }
            }
        }
        return clazz;
    }

    private boolean onLoadClass(String className) {
        for (String packageName : TRACKING_PROFILER_CLASS) {
            if (className.startsWith(packageName)) {
                return true;
            }
        }
        return false;
    }
}
