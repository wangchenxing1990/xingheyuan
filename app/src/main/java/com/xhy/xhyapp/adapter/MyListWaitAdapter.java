package com.xhy.xhyapp.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xhy.xhyapp.R;

import java.util.List;



/**
 * Created by Administrator on 2016/7/27.
 */
public class MyListWaitAdapter extends BaseAdapter{
    private final FragmentActivity activity;
    private final List<Integer> images;

    public MyListWaitAdapter(FragmentActivity activity, List<Integer> images) {
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
            view=View.inflate(activity, R.layout.wait_shipments_item, null);
            holder.iv= (ImageView) view.findViewById(R.id.iv_order);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }

        holder.iv.setImageResource(images.get(i));
        return view;

    }
    class ViewHolder{
        public ImageView iv;
    }
}
