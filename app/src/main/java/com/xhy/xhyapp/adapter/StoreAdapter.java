package com.xhy.xhyapp.adapter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhy.xhyapp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class StoreAdapter extends BaseAdapter{


    private final List<String> list;
    private final FragmentActivity activity;
    public StoreAdapter(FragmentActivity activity, List<String> list) {

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
      if (view==null) {
        holder=new ViewHolder();
        view=View.inflate(activity, R.layout.store_item, null);
        holder.store_text=(TextView) view.findViewById(R.id.store_text);
          view.setTag(holder);
      } else {// 有直接获得ViewHolder
          holder = (ViewHolder) view.getTag();
      }
        Log.e("@@@@@oooo@@@@@@@",list.toString());
        holder.store_text.setText(list.get(i).toString());

        return view;

    }



    class ViewHolder{
        private TextView store_text;

    }

}


