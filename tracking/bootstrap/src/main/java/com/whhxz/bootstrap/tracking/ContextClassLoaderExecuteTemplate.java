package com.whhxz.bootstrap.tracking;

import com.whhxz.bootstrap.tracking.exception.TrackingException;

import java.util.concurrent.Callable;

/**
 * ContextClassLoaderExecuteTemplate
 * 切换当前线程的类加载器
 * Created by xuzhuo on 2018/3/9.
 */
public class ContextClassLoaderExecuteTemplate<T> {
    private final ClassLoader classLoader;

    public ContextClassLoaderExecuteTemplate(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public T execute(Callable<T> callable) {
        Thread currentThread = Thread.currentThread();
        ClassLoader before = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(this.classLoader);
        try {
            return callable.call();
        } catch (Exception e) {
            throw new TrackingException(e.getMessage(), e);
        } finally {
            //还原当前线程的classLoader
            currentThread.setContextClassLoader(before);
        }
    }
}
