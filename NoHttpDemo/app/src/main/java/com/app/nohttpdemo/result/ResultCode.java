package com.app.nohttpdemo.result;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ResultCode {

    //1  操作成功
    //-1 业务处理异常
    //-2 数据格式异常
    //-3数据格式正确，数据异常
    public static final String SUCCESS = "1";
    public static final String ERROR_LOGIC = "-1";
    public static final String ERROR_DATA_FORMAT = "-2";
    public static final String ERROR_DATA = "-3";
}
