package com.cat.miao.model;

public class FeedBean {
    private String code;
    private String message;
    public Result result;

    public class Result{
        public String date;
        public String gmtModified;
        public Integer id;
        public String location;
        public Integer times;

        public String getLocation() {
            return location;
        }

        public Integer getTimes() {
            return times;
        }

        public String getGmtModified() {
            return date;
        }
    }

    public String getCode() {
        return code;
    }

    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
