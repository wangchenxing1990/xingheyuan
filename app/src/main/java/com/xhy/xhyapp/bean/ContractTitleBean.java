package com.xhy.xhyapp.bean;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ContractTitleBean {
    private String contractRitle;
    private String id;
    private  String addTime;
    private String url;
    private String goodsname;

    public String getGoodsstrack() {
        return goodsstrack;
    }

    public void setGoodsstrack(String goodsstrack) {
        this.goodsstrack = goodsstrack;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    private String goodsstrack;

    public String getContractRitle() {
        return contractRitle;
    }

    public void setContractRitle(String contractRitle) {
        this.contractRitle = contractRitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
