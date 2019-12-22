package com.cat.miao.model;

import java.util.ArrayList;

public class AdoptBean {
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

        public int getId(){
            return id;
        }
    }
}
