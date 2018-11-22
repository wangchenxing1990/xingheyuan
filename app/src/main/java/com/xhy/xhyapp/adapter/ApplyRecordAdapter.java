package com.xhy.xhyapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myactivity.ApplyDetailsActivity;
import com.xhy.xhyapp.myactivity.ApplyRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ApplyRecordAdapter extends BaseAdapter{

    public final FragmentActivity activity;
    public List<ApplyRecord> list=new ArrayList();
    public ViewGroup.LayoutParams para;


    public ApplyRecordAdapter(FragmentActivity activity, List<ApplyRecord> list) {
        this.activity=activity;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        //if (view==null) {
        holder=new ViewHolder();
        view=View.inflate(activity, R.layout.applyrecord_item, null);

        holder.huankuanbaifenbi=(TextView) view.findViewById(R.id.huankuanbaifenbi);
        holder.huankuanzonge=(TextView) view.findViewById(R.id.huankuanzonge);
        holder.huankuandianji=(TextView) view.findViewById(R.id.huankuandianji);
        holder.jiekuanriqi=(TextView) view.findViewById(R.id.jiekuanriqi);
        holder.huankuanjindutiao=(TextView) view.findViewById(R.id.huankuanjindutiao);
        holder.huankuan=(LinearLayout) view.findViewById(R.id.huankuan);
        holder.huankuanzongexiaoshu=(TextView) view.findViewById(R.id.huankuanzongexiaoshu);

        final ViewHolder viewHolder;
        viewHolder=holder;

        if ("0".equals(list.get(i).getState())){
            holder.huankuandianji.setText("立即还款");
        }else {
            holder.huankuandianji.setText("已完成");
        }
        holder.huankuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(activity, ApplyDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("merchantLoanid",list.get(i).getMerchantLoanid());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });


        holder.huankuanbaifenbi.setText("已还本金（"+list.get(i).getHuankuanbaifenbi()+"%)");
        holder.huankuanzonge.setText(list.get(i).getHuankuanzonge().substring(0,list.get(i).getHuankuanzonge().indexOf(".")));
        holder.jiekuanriqi.setText(list.get(i).getJiekuanriqi());
        holder.huankuanzongexiaoshu.setText(list.get(i).getHuankuanzonge().substring(list.get(i).getHuankuanzonge().lastIndexOf(".")));

        para = holder.huankuanjindutiao.getLayoutParams();
        para.width =100*Integer.parseInt(list.get(i).getHuankuanbaifenbi())/100*2;
        holder.huankuanjindutiao.setLayoutParams(para);

        return view;

    }

    private class TextOclick implements View.OnClickListener {
        int i;
        public TextOclick(int i) {
            this.i=i;
        }
        @Override
        public void onClick(View view) {
        }
    }



    class ViewHolder {
        private TextView huankuanzonge;//还款总额
        private TextView jiekuanriqi;//借款日期
        private TextView huankuanbaifenbi;//  还款百分比
        private TextView huankuanjindutiao;//还款进度条
        private TextView huankuandianji;//还款点击
        private LinearLayout huankuan;//还款外层布局
        private TextView huankuanzongexiaoshu;//还款总额小数
    }

}
