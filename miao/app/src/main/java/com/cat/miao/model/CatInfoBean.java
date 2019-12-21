package com.cat.miao.model;

import java.util.ArrayList;

public class CatInfoBean {
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
        int adopt;
        String bear;
        String color;
        int curpage;
        String gender;
        String gmtCreate;
        String gmtModified;
        String health;
        int id;
        String identify;
        String kind;
        int living;
        String location;
        String name;
        int pageSize;
        String relatives;
        String remarks;
        String url;

        public String getName(){
            return name;
        }

        public String getGender(){
            return gender;
        }

        public String getColor(){
            return color;
        }

        public String getKind(){
            return kind;
        }

        public String getLocation(){
            return location;
        }

        public String getUrl(){
            return url;
        }

        public int getID(){
            return id;
        }
    }

}
