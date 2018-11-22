package com.xhy.xhyapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22 0022.
 */

public class AddressBean implements Serializable {
    private String consignee;
    private String tel;
    private String address;
    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public AddressBean() {
    }

    public AddressBean(String consignee, String tel, String address,String addressId) {
        this.consignee = consignee;
        this.tel = tel;
        this.address = address;
        this.addressId = addressId;
    }

    public String getConsignee() {
        return consignee;
    }
    @JSONField(name = "consignee")
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTel() {
        return tel;
    }
    @JSONField(name = "tel")
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }
    @JSONField(name = "address")
    public void setAddress(String address) {
        this.address = address;
    }
}
