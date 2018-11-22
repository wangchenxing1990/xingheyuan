package com.xhy.xhyapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class LoginBean implements Serializable {
    private String state;
    private String msg;
    private String userName;
    private String password;

    public LoginBean(String state, String userName, String msg, String password) {
        this.state = state;
        this.userName = userName;
        this.msg = msg;
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
