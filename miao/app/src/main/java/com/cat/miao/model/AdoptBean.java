package com.cat.miao.model;

public class AdoptBean {
    private String code;
    private String message;
    private Result result;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result getResult() {
        return result;
    }

    public class Result{
        int adopterId;
        String blog;
        int catId;
        int id;

        public int getCatId(){
            return catId;
        }

        public String getBlog(){
            return blog;
        }
    }
}
