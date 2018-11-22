package com.xhy.xhyapp.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.storeactivity.LogisticsActivity;

import java.util.List;


/**
 * Created by Administrator on 2016/7/27.
 */
public class MyListSendAdapter extends BaseAdapter{
    private final FragmentActivity activity;
    private final List<Integer> images;

    public MyListSendAdapter(FragmentActivity activity, List<Integer> images) {
        this.activity=activity;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null) {
            holder=new ViewHolder();
            view=View.inflate(activity, R.layout.had_send_item, null);
            holder.iv= (ImageView) view.findViewById(R.id.iv_had_send_item);
            holder.tv_look_information= (TextView) view.findViewById(R.id.tv_look_logistics_information);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }

        holder.iv.setImageResource(images.get(i));

        final ViewHolder finalHolder = holder;
        holder.tv_look_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,LogisticsActivity.class);
                activity.startActivity(intent);
            }


        });
        return view;

    }
    class ViewHolder{
        public ImageView iv;
        public TextView tv_look_information;
    }
}

