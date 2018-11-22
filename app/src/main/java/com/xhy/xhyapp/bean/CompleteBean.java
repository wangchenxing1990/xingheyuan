package com.xhy.xhyapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class CompleteBean implements Serializable {
    private String goodsName;
    private String orderId;
    private String goodsNumber;
    private String orderCode;
    private String orderState;
    private String goodsId;
    private String thumbnailImg;
    private String totalMoney;
    private String unitPrice;
    private String address;
    private String isRemind;
    private String expressPrice;
    private String isConfirm;
    private String consignee;

    public CompleteBean() {
    }


    public CompleteBean(String goodsName, String expressPrice, String unitPrice, String totalMoney, String orderCode, String goodsId, String thumbnailImg, String orderId, String goodsNumber, String orderState, String address,
                        String isRemind, String isConfirm,
                        String consignee) {
        this.goodsName = goodsName;
        this.expressPrice = expressPrice;
        this.unitPrice = unitPrice;
        this.totalMoney = totalMoney;
        this.orderCode = orderCode;
        this.goodsId = goodsId;
        this.thumbnailImg = thumbnailImg;
        this.orderId = orderId;
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsName() {
        return goodsName;
    }

    @JSONField(name = "goodsName")
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderId() {
        return orderId;
    }

    @JSONField(name = "orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    @JSONField(name = "goodsNumber")
    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getOrderCode() {
        return orderCode;
    }

    @JSONField(name = "orderCode")
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsId() {
        return goodsId;
    }

    @JSONField(name = "goodsId")
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    @JSONField(name = "thumbnailImg")
    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    @JSONField(name = "totalMoney")
    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    @JSONField(name = "unitPrice")
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    @JSONField(name = "expressPrice")
    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }
}
