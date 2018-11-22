package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/10.
 */
public class CollectionBean {
    public String collectionId;
    public String goodsId;
    public String goodsType;
    public String goodsName;
    public String thumbnailImg;
    public String stock;
    public String ninNum;
    public String minPrice;

    public CollectionBean() {
        this.collectionId = collectionId;
        this.minPrice = minPrice;
        this.ninNum = ninNum;
        this.stock = stock;
        this.thumbnailImg = thumbnailImg;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsId = goodsId;
    }

    public CollectionBean(String collectionId, String minPrice, String ninNum, String stock, String thumbnailImg, String goodsName, String goodsType, String goodsId) {
        this.collectionId = collectionId;
        this.minPrice = minPrice;
        this.ninNum = ninNum;
        this.stock = stock;
        this.thumbnailImg = thumbnailImg;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsId = goodsId;
    }



    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getNinNum() {
        return ninNum;
    }

    public void setNinNum(String ninNum) {
        this.ninNum = ninNum;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}
