package com.app.nohttpdemo.result;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ResponseBody implements Serializable {

    private static final long serialVersionUID = -5924608897513416051L;
    private String resultCode;
    private String errorMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
