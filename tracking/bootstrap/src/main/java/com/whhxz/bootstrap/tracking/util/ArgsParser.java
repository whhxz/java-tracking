package com.whhxz.bootstrap.tracking.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ArgsParser
 * Created by xuzhuo on 2018/3/7.
 */
public class ArgsParser {
    public static Map<String, String> parse(String args) {
        if (isEmpty(args)) {
            return Collections.emptyMap();
        }

        final Map<String, String> map = new HashMap<String, String>();

        Scanner scanner = new Scanner(args);
        scanner.useDelimiter("\\s*,\\s*");

        while (scanner.hasNext()) {
            String token = scanner.next();
            int assign = token.indexOf('=');

            if (assign == -1) {
                map.put(token, "");
            } else {
                String key = token.substring(0, assign);
                String value = token.substring(assign + 1);
                map.put(key, value);
            }
        }
        scanner.close();
        return Collections.unmodifiableMap(map);
    }

    private static boolean isEmpty(String args) {
        return args == null || args.isEmpty();
    }

}
