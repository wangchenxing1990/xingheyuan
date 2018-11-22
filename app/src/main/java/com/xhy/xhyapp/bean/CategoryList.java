package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CategoryList {

    public String goodsId;
    public String goodsName;
    public String goodsType;
    public String thumbnailImg;
    public String label;
    public String volume;
    public String stock;
    public String minNum;
    public String minPrice;
    public String collectionId;
    public String goodsStyleType;
    public String sumStock;



    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getGoodsStyleType() {
        return goodsStyleType;
    }

    public void setGoodsStyleType(String goodsStyleType) {
        this.goodsStyleType = goodsStyleType;
    }

    public CategoryList(String goodsId, String volume, String label, String thumbnailImg, String goodsType, String goodsName, String stock, String minNum, String minPrice, String collectionId, String goodsStyleType,String sumStock) {
        this.goodsId = goodsId;
        this.volume = volume;
        this.label = label;
        this.thumbnailImg = thumbnailImg;
        this.goodsType = goodsType;
        this.goodsName = goodsName;
        this.stock = stock;
        this.minNum = minNum;
        this.minPrice = minPrice;
        this.collectionId=collectionId;
        this.goodsStyleType=goodsStyleType;
        this.sumStock=sumStock;


    }

    public CategoryList() {

    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getStock() {
        return stock;
    }
    public String getSumStock() {
        return sumStock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    public void setSumStock(String sumStock) {
        this.sumStock = sumStock;
    }
}
