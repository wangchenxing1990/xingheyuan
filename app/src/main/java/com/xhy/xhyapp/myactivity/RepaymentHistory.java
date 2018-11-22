package com.xhy.xhyapp.myactivity;

/**
 * Created by Administrator on 2016/8/27.
 */
public class RepaymentHistory {

    private String huankuancishu;//还款次数
    private String huankuanriqi;//还款日期
    private String huankuanjine;//还款金额


    public String getHuankuancishu() {
        return huankuancishu;
    }

    public void setHuankuancishu(String huankuancishu) {
        this.huankuancishu = huankuancishu;
    }

    public String getHuankuanriqi() {
        return huankuanriqi;
    }

    public void setHuankuanriqi(String huankuanriqi) {
        this.huankuanriqi = huankuanriqi;
    }

    public String getHuankuanjine() {
        return huankuanjine;
    }

    public void setHuankuanjine(String huankuanjine) {
        this.huankuanjine = huankuanjine;
    }

    @Override
    public String toString() {
        return "RepaymentHistory{" +
                "huankuancishu='" + huankuancishu + '\'' +
                ", huankuanriqi='" + huankuanriqi + '\'' +
                ", huankuanjine='" + huankuanjine + '\'' +
                '}';
    }

}
