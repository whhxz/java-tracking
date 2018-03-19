package com.whhxz.bootstrap.tracking;

import com.whhxz.bootstrap.tracking.config.TrackingConfig;
import com.whhxz.bootstrap.tracking.util.ArgsParser;
import com.whhxz.bootstrap.tracking.util.FileUtils;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * PreMain
 * Created by xuzhuo on 2018/3/7.
 */
public class PreMain {
    private final static Logger logger = Logger.getLogger(PreMain.class.getName());

    public static void premain(String args, Instrumentation instrumentation) {
        Map<String, String> argMap = null;
        if (args == null) {
            logger.warning("args is null, need agentId and applicationName");
        } else {
            argMap = ArgsParser.parse(args);
        }
        ClassPathResolver classPathResolver = new ClassPathResolver();
        if (!classPathResolver.initPath()) {
            logger.warning("class path can't find must file");
            return;
        }
        JarFile bootstrapCoreJarFile = FileUtils.fileToJarFile(classPathResolver.getBootstrapCoreJarPath());
        instrumentation.appendToBootstrapClassLoaderSearch(bootstrapCoreJarFile);

        String agentConfigPath = classPathResolver.findAgentConfigPath();
        TrackingConfig trackingConfig = TrackingConfig.load(agentConfigPath);
        trackingConfig.addArgs(argMap);
        //设置日志文件
        trackingConfig.setLog4jFile(classPathResolver.findLog4jFile());

        List<URL> libs = classPathResolver.resolveLib();
        AgentClassLoader agentClassLoader = new AgentClassLoader(libs);
        logger.info("agent start ...");
        agentClassLoader.boot(trackingConfig, instrumentation);
        logger.info("agent end ...");
    }


}
