package com.app.nohttpdemo.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by User_Kira on 2016/6/29.
 */
public class GsonUtils {
    public static <T> T parseResponse(String resp, Type typeofT) {
        try {
            Gson gson = new Gson();
            return (T) gson.fromJson(resp, typeofT);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
