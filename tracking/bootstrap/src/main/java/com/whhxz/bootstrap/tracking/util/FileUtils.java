package com.whhxz.bootstrap.tracking.util;

import com.whhxz.bootstrap.tracking.exception.TrackingException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * FileUtils
 * Created by xuzhuo on 2018/3/8.
 */
public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class.getName());

    public static JarFile fileToJarFile(String filePath) {
        try {
            return new JarFile(filePath);
        } catch (IOException e) {
            logger.warning("file :" + filePath + " is not jarFile");
            throw new TrackingException(e.getMessage(), e);
        }
    }

    public static URL fileToURL(File file){
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            logger.warning("file to url error");
            throw new TrackingException(e.getMessage(), e);
        }
    }
}
