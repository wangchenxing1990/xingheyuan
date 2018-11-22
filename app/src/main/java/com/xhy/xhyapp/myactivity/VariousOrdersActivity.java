package com.xhy.xhyapp.myactivity;

/**
 * Created by Administrator on 2016/8/8.
 */
public class VariousOrdersActivity {

    private String goodsName;//商品名称
    private String totalMoney;//商品总金额
    private String expressPrice;//快递价格
    private String unitPrice;//商品单价
    private String isRemind;//是否提醒发货
    private String isCondirm;//是否确认收货
    private String goodsNumber;//商品数量
    private String thumbnailImg;//商品图片
    private int orderId;//订单ID

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }


    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }


    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public String getIsCondirm() {
        return isCondirm;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    public void setIsCondirm(String isCondirm) {
        this.isCondirm = isCondirm;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "VariousOrdersActivity{" +
                "goodsName='" + goodsName + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", expressPrice='" + expressPrice + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", isRemind='" + isRemind + '\'' +
                ", isCondirm='" + isCondirm + '\'' +
                ", goodsNumber='" + goodsNumber + '\'' +
                ", thumbnailImg='" + thumbnailImg + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
