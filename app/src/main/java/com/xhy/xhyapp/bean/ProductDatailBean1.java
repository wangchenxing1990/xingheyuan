package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ProductDatailBean1 {
    private String title;
    private String brand;
    private String place;
    private String grade;
    private String brandIntroduction;
    private String label;
    private String goodsId;
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public ProductDatailBean1(String title, String brand, String place, String grade, String brandIntroduction, String label, String goodsId) {
        this.title = title;
        this.brand = brand;
        this.place = place;
        this.grade = grade;
        this.brandIntroduction = brandIntroduction;
        this.label = label;
    }

    public ProductDatailBean1( ) {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBrandIntroduction() {
        return brandIntroduction;
    }

    public void setBrandIntroduction(String brandIntroduction) {
        this.brandIntroduction = brandIntroduction;
    }
}
