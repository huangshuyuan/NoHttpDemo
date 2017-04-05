package com.app.nohttpdemo.utils;

import android.content.Context;

import java.util.Properties;

/**
 * 读取配置文件
 */

public class PropertyUtils {

    //    进行配置文件读取
    public static String read(Context context, String key, String fileName) {
        Properties properties = new Properties();
        String attr = null;

        try {
            properties.load(context.getAssets().open(fileName));
            attr = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attr;


    }

    //    进行配置文件读取IP
    public static String getIP(Context context) {
        Properties properties = new Properties();
        String attr = null;

        try {
            properties.load(context.getAssets().open("ip.properties"));
            attr = properties.getProperty("ip");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attr;


    }
}

