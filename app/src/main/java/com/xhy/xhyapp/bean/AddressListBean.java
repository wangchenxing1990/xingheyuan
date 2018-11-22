package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/9/1.
 */
public class AddressListBean {
    private String addressId;
    private String title;
    private String consignee;
    private String tel;
    private String address;
    private String isDefault;

    public AddressListBean(String addressId, String title, String consignee, String tel, String address, String isDefault) {
        this.addressId = addressId;
        this.title = title;
        this.consignee = consignee;
        this.tel = tel;
        this.address = address;
        this.isDefault = isDefault;
    }

    public AddressListBean() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
