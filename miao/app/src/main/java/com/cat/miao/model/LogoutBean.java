package com.cat.miao.model;

public class LogoutBean {
    private String code;
    private String message;
    private Result result;

    private class Result{

    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
