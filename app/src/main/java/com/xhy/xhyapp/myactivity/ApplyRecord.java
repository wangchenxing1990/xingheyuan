package com.xhy.xhyapp.myactivity;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ApplyRecord {

    private String huankuanzonge;//还款总额
    private String jiekuanriqi;//借款日期
    private String huankuanbaifenbi;//还款百分比
    private String merchantLoanid;//贷款ID
    private String state;//还款状态

    public String getMerchantLoanid() {
        return merchantLoanid;
    }

    public void setMerchantLoanid(String merchantLoanid) {
        this.merchantLoanid = merchantLoanid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHuankuanzonge(String huankuanzonge) {
        this.huankuanzonge = huankuanzonge;
    }

    public void setJiekuanriqi(String jiekuanriqi) {
        this.jiekuanriqi = jiekuanriqi;
    }

    public void setHuankuanbaifenbi(String huankuanbaifenbi) {
        this.huankuanbaifenbi = huankuanbaifenbi;
    }

    public String getHuankuanzonge() {

        return huankuanzonge;
    }

    public String getJiekuanriqi() {
        return jiekuanriqi;
    }

    public String getHuankuanbaifenbi() {
        return huankuanbaifenbi;
    }

    @Override
    public String toString() {
        return "ApplyRecord{" +
                "huankuanzonge='" + huankuanzonge + '\'' +
                ", jiekuanriqi='" + jiekuanriqi + '\'' +
                ", huankuanbaifenbi='" + huankuanbaifenbi + '\'' +
                ", merchantLoanid='" + merchantLoanid + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
