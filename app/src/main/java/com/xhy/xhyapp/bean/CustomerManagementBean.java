package com.xhy.xhyapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class CustomerManagementBean implements Serializable {
    private String customerId;
    private String type;
    private String value;
    private int icon_id;
    private String title;
    private String msg;
    private String time;

    public CustomerManagementBean(String type, String customerId, String value,String title, String msg) {
        this.type = type;
        this.customerId = customerId;
        this.value = value;
        this.title = title;
        this.msg = msg;
    }

    public int getIcon_id() {
        return icon_id;
    }
    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public CustomerManagementBean() {
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
