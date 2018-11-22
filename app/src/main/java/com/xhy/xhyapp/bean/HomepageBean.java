package com.xhy.xhyapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class HomepageBean implements Serializable{
    private String image;
    private String name;
    private String jieshao;
    private String price;
    private String goodsId;
    private String goodsStyleType;


    public HomepageBean() {
    }

    public HomepageBean(String image, String name, String jieshao, String price,String goodsId,String goodsStyleType) {
        this.image = image;
        this.name = name;
        this.jieshao = jieshao;
        this.price = price;
        this.goodsId = goodsId;
        this.goodsStyleType=goodsStyleType;
    }

    public String getGoodsStyleType() {
        return goodsStyleType;
    }
    @JSONField(name = "goodsStyleType")
    public void setGoodsStyleType(String goodsStyleType) {
        this.goodsStyleType = goodsStyleType;
    }

    public String getImage() {
        return image;
    }
    @JSONField(name = "adsImg")
    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    @JSONField(name = "adsName")
    public void setName(String name) {
        this.name = name;
    }

    public String getJieshao() {
        return jieshao;
    }
    @JSONField(name = "adsTitle")
    public void setJieshao(String jieshao) {
        this.jieshao = jieshao;
    }

    public String getPrice() {
        return price;
    }
    @JSONField(name = "price")
    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

}
