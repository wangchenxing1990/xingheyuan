package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/31.
 */
public class RefundBean {
    private String goodsName;
    private String orderId;
    private String orderState;
    private String totalMoney;
    private String goodsNumber;
    private String goodsId;
    private String thumbnailImg;
    private String unitPrice;

    public RefundBean(String goodsName, String orderId, String orderState, String totalMoney, String goodsNumber, String goodsId, String thumbnailImg, String unitPrice) {
        this.goodsName = goodsName;
        this.orderId = orderId;
        this.orderState = orderState;
        this.totalMoney = totalMoney;
        this.goodsNumber = goodsNumber;
        this.goodsId = goodsId;
        this.thumbnailImg = thumbnailImg;
        this.unitPrice = unitPrice;
    }

    public RefundBean(){

    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
