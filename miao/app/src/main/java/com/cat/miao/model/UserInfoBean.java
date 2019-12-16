package com.cat.miao.model;

import java.util.ArrayList;

public class UserInfoBean {
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
        Integer id;
        String name;
        String password;
        String gender;
        String phone;
        String email;
        String wxUnionId;
        String image;
        Integer auth;

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }
    }
}
