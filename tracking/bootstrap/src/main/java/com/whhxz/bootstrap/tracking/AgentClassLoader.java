package com.whhxz.bootstrap.tracking;

import com.whhxz.bootstrap.tracking.config.TrackingConfig;
import com.whhxz.bootstrap.tracking.exception.TrackingException;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;

/**
 * AgentClassLoader
 * Created by xuzhuo on 2018/3/8.
 */
public class AgentClassLoader {
    private URLClassLoader classLoader;

    private Agent agentBootstrap;

    private ContextClassLoaderExecuteTemplate<Object> executeTemplate;

    public AgentClassLoader(List<URL> urls) {
        if (urls == null) {
            throw new NullPointerException("lib url is null");
        }
        this.classLoader = createClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
        this.executeTemplate = new ContextClassLoaderExecuteTemplate<>(classLoader);
    }

    public void boot(TrackingConfig trackingConfig, Instrumentation instrumentation) {
        String defaultBootstrap = trackingConfig.getDefaultBootstrap();
        Class<?> bootstrapClazz = createBootstrap(defaultBootstrap);
        Object agent = executeTemplate.execute(() -> {
            Constructor<?> constructor = bootstrapClazz.getConstructor(TrackingConfig.class, Instrumentation.class);
            return constructor.newInstance(trackingConfig, instrumentation);
        });
        if (agent instanceof Agent) {
            this.agentBootstrap = (Agent) agent;
        } else {
            throw new TrackingException("Invalid AgentType. boot failed. AgentClass "  + defaultBootstrap);
        }
    }

    private Class<?> createBootstrap(String className) {
        try {
            return this.classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new TrackingException(e.getMessage(), e);
        }
    }

    private URLClassLoader createClassLoader(URL[] urls, ClassLoader bootstrapClassLoader) {
        TrackingURLClassLoader trackingURLClassLoader = new TrackingURLClassLoader(urls, bootstrapClassLoader);
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged((PrivilegedAction<URLClassLoader>) () -> trackingURLClassLoader);
        } else {
            return trackingURLClassLoader;
        }
    }
}
