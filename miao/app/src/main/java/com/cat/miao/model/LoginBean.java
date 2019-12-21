package com.cat.miao.model;

public class LoginBean {
    private String code;
    private String message;
    public Result result;

    public class Result{
        private Integer id;
        public String name;
        private String password;
        private Integer gender;
        public String phone;
        public String email;
        private String wxUnionId;
        public String image;
        private Integer auth;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getImage() {
            return image;
        }
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

    public Result getResult(){
        return result;
    }
}
