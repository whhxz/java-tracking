package com.whhxz.bootstrap.tracking;

import com.whhxz.bootstrap.tracking.util.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassPathResolver
 * Created by xuzhuo on 2018/3/7.
 */
public class ClassPathResolver {
    private static final Logger logger = Logger.getLogger(ClassPathResolver.class.getName());
    static final Pattern DEFAULT_AGENT_PATTERN = Pattern.compile("tracking-bootstrap(-[0-9]+\\.[0-9]+((\\-SNAPSHOT)|(-RC[0-9]+))?)?.jar");
    private static final Pattern DEFAULT_AGENT_CORE_PATTERN = Pattern.compile("tracking-bootstrap-core(-[0-9]+\\.[0-9]+((\\-SNAPSHOT)|(-RC[0-9]+))?)?.jar");
    private static final String DEFAULT_CONFIG_NAME = "tracking.conf";

    //类路径
    private String classPath;
    private String agentDirPath;
    private String agentJarName;
    private String agentJarFullPath;
    private String bootstrapCoreJarPath;


    public ClassPathResolver() {
        this(System.getProperty("java.class.path"));
    }

    public ClassPathResolver(String classPath) {
        this.classPath = classPath;
    }

    public boolean initPath() {
        Matcher matcher = DEFAULT_AGENT_PATTERN.matcher(classPath);
        if (!matcher.find()) {
            return false;
        }
        this.agentJarName = findAgentJar(matcher);
        this.agentJarFullPath = findAgentJarPath(this.classPath, this.agentJarName);
        this.agentDirPath = parseAgentDirPath(this.agentJarFullPath);
        this.bootstrapCoreJarPath = parseBootstrapCoreJar(this.agentDirPath);
        return this.agentJarFullPath != null
                && this.agentDirPath != null
                && this.bootstrapCoreJarPath != null;
    }

    private String findAgentJar(Matcher matcher) {
        return this.classPath.substring(matcher.start(), matcher.end());
    }

    private String findAgentJarPath(String classPath, String jarName) {
        String[] classPathList = classPath.split(File.pathSeparator);
        for (String path : classPathList) {
            if (path.contains(jarName)) {
                return path;
            }
        }
        return null;
    }

    private String parseAgentDirPath(String agentJarFullPath) {
        int i1 = agentJarFullPath.lastIndexOf("/");
        int i2 = agentJarFullPath.lastIndexOf("\\");
        int max = Math.max(i1, i2);
        return max == -1 ? null : agentJarFullPath.substring(0, max);
    }

    private String parseBootstrapCoreJar(String agentDirPath) {
        String libPath = findLibPath(agentDirPath);
        String bootstrapCoreName = null;
        String[] children = new File(libPath).list();
        if (children != null) {
            for (String childFileName : children) {
                if (DEFAULT_AGENT_CORE_PATTERN.matcher(childFileName).matches()) {
                    bootstrapCoreName = childFileName;
                    break;
                }
            }
        }
        return bootstrapCoreName == null ? null : libPath + File.separator + bootstrapCoreName;
    }

    public String findLibPath(String agentDirPath) {
        return agentDirPath + File.separator + "libs";
    }

    public String getBootstrapCoreJarPath() {
        return bootstrapCoreJarPath;
    }

    public String findAgentConfigPath() {
        String configPath = System.getProperty(DEFAULT_CONFIG_NAME);
        return configPath != null ? configPath : agentDirPath + File.separator + DEFAULT_CONFIG_NAME;
    }

    public List<URL> resolveLib() {
        String libPath = findLibPath(this.agentDirPath);
        File libDir = new File(libPath);
        if (!libDir.exists() || !libDir.isDirectory()) {
            logger.warning("agent lib is error");
            return Collections.emptyList();
        }
        File[] jarFiles = libDir.listFiles(pathname -> {
            String fileName = pathname.getName();
            if (fileName.lastIndexOf(".jar") != -1
                    || fileName.lastIndexOf(".xml") != -1
                    || fileName.lastIndexOf(".properties") != -1) {
                return true;
            }
            return false;
        });
        List<URL> jarList = new ArrayList<>();
        if (jarFiles != null) {
            for (File file : jarFiles) {
                jarList.add(FileUtils.fileToURL(file));
            }
        }
        return jarList;
    }

    public String findLog4jFile() {
        return findLibPath(this.agentDirPath) + File.separator + "log4j.properties";
    }
}
