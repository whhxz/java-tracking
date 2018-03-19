package com.whhxz.bootstrap.config;

import com.whhxz.bootstrap.tracking.config.TrackingConfig;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * TrackingConfigTest
 * Created by xuzhuo on 2018/3/7.
 */
public class TrackingConfigTest {
    @Test
    public void load() throws Exception {
        TrackingConfig.load("/Users/xuzhuo/Documents/git/whhxz/java-tracking/tracking/tracking.conf");
    }
    @Test
    public void file()throws Exception{
        File file = new File("/Users/xuzhuo/Documents/git/whhxz/java-tracking/tracking/bootstrap/src/main/java/com/whhxz/bootstrap");
        for (String s : file.list()) {
            System.out.println(s);
        }
    }

}