package com.xhy.xhyapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class ShopDetailBean implements Serializable{
private String state;
    private String realName;
    private String shopName;
    private String shopScore;
    private String shopLevel;
    private String tel;
    private String headPic;

    public ShopDetailBean() {
    }

    public ShopDetailBean(String state, String realName, String shopName, String shopScore, String shopLevel, String tel, String headPic) {
        this.state = state;
        this.realName = realName;
        this.shopName = shopName;
        this.shopScore = shopScore;
        this.shopLevel = shopLevel;
        this.tel = tel;
        this.headPic = headPic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopScore() {
        return shopScore;
    }

    public void setShopScore(String shopScore) {
        this.shopScore = shopScore;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeadPic() {
        return headPic;
    }
    @JSONField(name = "setHeadPic")
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
