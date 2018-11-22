package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhy.xhyapp.R;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class PingtaiAdapter extends BaseAdapter{
    String[] str={"商品活动","消息通知","商品活动","消息通知","商品活动","消息通知"};
    Context context;
    public PingtaiAdapter( Context context, String[] str) {
        this.context=context;
        this.str=str;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int i) {
        return str[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.pingtai_item,null);
            holder.txt_notice= ((TextView) view.findViewById(R.id.textView_notice));
            holder.txt_notice.setText(str[i]);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }
    class ViewHolder{
        TextView txt_notice;
    }
}
