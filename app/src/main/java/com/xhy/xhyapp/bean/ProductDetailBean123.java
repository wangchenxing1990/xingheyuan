package com.xhy.xhyapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ProductDetailBean123 implements Serializable{
    private String stock;
    private String goodsTypesId;
    private String num1;
    private String num2;
    private String num3;
    private String unitPrice1;
    private String unitPrice2;
    private String unitPrice3;
    private String goodsTypeName;
    private String goodsId;

    public ProductDetailBean123(String stock, String unitPrice3, String unitPrice2, String unitPrice1, String num3, String num2, String num1, String goodsTypeName, String goodsTypesId, String goodsId) {
        this.stock = stock;
        this.unitPrice3 = unitPrice3;
        this.unitPrice2 = unitPrice2;
        this.unitPrice1 = unitPrice1;
        this.num3 = num3;
        this.num2 = num2;
        this.num1 = num1;
        this.goodsTypeName = goodsTypeName;
        this.goodsTypesId = goodsTypesId;
        this.goodsId = goodsId;

    }
    public ProductDetailBean123() {

    }
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getGoodsTypesId() {
        return goodsTypesId;
    }

    public void setGoodsTypesId(String goodsTypesId) {
        this.goodsTypesId = goodsTypesId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getUnitPrice1() {
        return unitPrice1;
    }

    public void setUnitPrice1(String unitPrice1) {
        this.unitPrice1 = unitPrice1;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getUnitPrice2() {
        return unitPrice2;
    }

    public void setUnitPrice2(String unitPrice2) {
        this.unitPrice2 = unitPrice2;
    }

    public String getUnitPrice3() {
        return unitPrice3;
    }

    public void setUnitPrice3(String unitPrice3) {
        this.unitPrice3 = unitPrice3;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
