package com.cat.miao.model;

import java.util.ArrayList;

public class AdoptInfoBean {
    private String code;
    private String message;
    private ArrayList<Result> result;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public class Result{
        int catId;
        int examinedId;
        int Id;
        String reason;
        int status;
        int userId;

        public int getCatId(){
            return catId;
        }

        public int status(){
            return status;
        }
    }

}
