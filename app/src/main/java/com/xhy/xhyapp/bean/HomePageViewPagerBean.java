package com.xhy.xhyapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class HomePageViewPagerBean implements Serializable {
    private String image;

    public HomePageViewPagerBean() {
    }

    public String getImage() {
        return image;
    }
    @JSONField(name = "adsImg")
    public void setImage(String image) {
        this.image = image;
    }
}
