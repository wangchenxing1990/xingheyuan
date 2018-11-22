package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/9.
 */
public class Category {
    public String categoryName;
    public String icon;
    public String categoryId;

    public Category(){
        this.categoryName = categoryName;
        this.icon = icon;
        this.categoryId=categoryId;
    }

    public Category(String categoryName, String icon) {
        this.categoryName = categoryName;
        this.icon = icon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
