package com.whhxz.bootstrap.tracking.config;

import com.whhxz.bootstrap.tracking.exception.TrackingException;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * TrackingConfig
 * Created by xuzhuo on 2018/3/7.
 */
public class TrackingConfig {
    private String agentId;
    private String applicationName;

    private static String DEFAULT_BOOTSTRAP = "com.whhxz.tracking.profiler.DefaultAgent";

    private Properties properties;

    private String log4jFile;

    private TrackingConfig() {
    }

    public static TrackingConfig load(String filePath) {
        TrackingConfig trackingConfig = new TrackingConfig();;
        try {
            Properties properties = null;
            try (Reader reader = new InputStreamReader(new FileInputStream(filePath), "utf-8")) {
                properties = new Properties();
                properties.load(reader);
            }
            trackingConfig.agentId = properties.getProperty("agentId");
            trackingConfig.applicationName = properties.getProperty("applicationName");
            trackingConfig.properties = properties;
        } catch (IOException e) {
            throw new TrackingException(e.getMessage(), e);
        }
        return trackingConfig;
    }

    public void addArgs(Map<String, String> args) {
        if (args != null) {
            properties.putAll(args);
        }
    }

    public String getLog4jFile() {
        return log4jFile;
    }

    public void setLog4jFile(String log4jFile) {
        this.log4jFile = log4jFile;
    }

    public String getDefaultBootstrap() {
        return DEFAULT_BOOTSTRAP;
    }
}
