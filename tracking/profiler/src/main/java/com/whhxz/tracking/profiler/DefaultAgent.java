package com.whhxz.tracking.profiler;

import com.whhxz.bootstrap.tracking.Agent;
import com.whhxz.bootstrap.tracking.ByteCodeInterceptor;
import com.whhxz.bootstrap.tracking.config.TrackingConfig;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * DefaultAgent
 * Created by xuzhuo on 2018/3/9.
 */
public class DefaultAgent implements Agent {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAgent.class);

    private final TrackingConfig trackingConfig;
    private final Instrumentation instrumentation;

    private final ClassFileTransformer classFileTransformer;

    private final ByteCodeInterceptor byteCodeInterceptor;

    public DefaultAgent(TrackingConfig trackingConfig, Instrumentation instrumentation) {
        PropertyConfigurator.configure(trackingConfig.getLog4jFile());

        this.trackingConfig = trackingConfig;
        this.instrumentation = instrumentation;

        this.byteCodeInterceptor = new JavaAssistByteCodeInterceptor(this);

        this.classFileTransformer = new ClassFileTransformerDispatcher(this);
        this.start();
    }

    @Override
    public void start() {
        logger.info("trackingConfig");
    }

    @Override
    public void stop() {

    }
}
