package com.xhy.xhyapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ProductDtailBean;
import com.xhy.xhyapp.purchaseactivity.IconTextDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MyGridAdapter extends BaseAdapter {
    private final IconTextDetailActivity iconTextDetailActivity;
    private final List<ProductDtailBean> user;

    public MyGridAdapter(IconTextDetailActivity iconTextDetailActivity, List<ProductDtailBean> user) {
        this.iconTextDetailActivity = iconTextDetailActivity;
        this.user = user;
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int i) {
        return user.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(iconTextDetailActivity, R.layout.pro_para_list, null);
            holder.tv_nutrition_para = (TextView) view.findViewById(R.id.tv_nutrition_para);
            holder.tv_nutrition_weight = (TextView) view.findViewById(R.id.tv_nutrition_weight);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_nutrition_para.setText(user.get(i).getName());
        holder.tv_nutrition_weight.setText(user.get(i).getValue());

        return view;
    }

    class ViewHolder {
        public TextView tv_nutrition_para, tv_nutrition_weight;
    }
}