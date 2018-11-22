package com.xhy.xhyapp.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ProductDtailBean implements Serializable {
    private String goodsId;
    private String stock;
    private String goodsTypesId;
    private String goodsTypeName;
    private String num1;
    private String num2;
    private String num3;
    private String unitPrice1;
    private String unitPrice2;
    private String unitPrice3;
    private String name;
    private String value;
    private String goodsDetailImgList;
    private String goodsHeaderImgList;

    public ProductDtailBean(String goodsId, String stock, String goodsTypesId, String goodsTypeName, String num1, String num2, String num3, String unitPrice1, String unitPrice2, String unitPrice3, String name, String value, String goodsDetailImgList, String goodsHeaderImgList) {
        this.goodsId = goodsId;
        this.stock = stock;
        this.goodsTypesId = goodsTypesId;
        this.goodsTypeName = goodsTypeName;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.unitPrice1 = unitPrice1;
        this.unitPrice2 = unitPrice2;
        this.unitPrice3 = unitPrice3;
        this.name = name;
        this.value = value;
        this.goodsDetailImgList = goodsDetailImgList;
        this.goodsHeaderImgList = goodsHeaderImgList;
    }

    public ProductDtailBean() {
        this.goodsId = goodsId;
        this.stock = stock;
        this.goodsTypesId = goodsTypesId;
        this.goodsTypeName = goodsTypeName;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.unitPrice1 = unitPrice1;
        this.unitPrice2 = unitPrice2;
        this.unitPrice3 = unitPrice3;
        this.name = name;
        this.value = value;
        this.goodsDetailImgList = goodsDetailImgList;
        this.goodsHeaderImgList = goodsHeaderImgList;
    }
    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsTypesId() {
        return goodsTypesId;
    }

    public void setGoodsTypesId(String goodsTypesId) {
        this.goodsTypesId = goodsTypesId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsHeaderImgList() {
        return goodsHeaderImgList;
    }

    public void setGoodsHeaderImgList(String goodsHeaderImgList) {
        this.goodsHeaderImgList = goodsHeaderImgList;
    }

    public String getGoodsDetailImgList() {
        return goodsDetailImgList;
    }

    public void setGoodsDetailImgList(String goodsDetailImgList) {
        this.goodsDetailImgList = goodsDetailImgList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice3() {
        return unitPrice3;
    }

    public void setUnitPrice3(String unitPrice3) {
        this.unitPrice3 = unitPrice3;
    }

    public String getUnitPrice2() {
        return unitPrice2;
    }

    public void setUnitPrice2(String unitPrice2) {
        this.unitPrice2 = unitPrice2;
    }

    public String getUnitPrice1() {
        return unitPrice1;
    }

    public void setUnitPrice1(String unitPrice1) {
        this.unitPrice1 = unitPrice1;
    }


}
