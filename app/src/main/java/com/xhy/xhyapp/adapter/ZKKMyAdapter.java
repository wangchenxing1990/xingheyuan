package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xhy.xhyapp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ZKKMyAdapter extends BaseAdapter {

    public List<String> list;
    public Context context;

   public ZKKMyAdapter(Context context, List list){
       this.context=context;
       this.list=list;
   }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= View.inflate(context, R.layout.zkkgengxin_listview,null);

            holder.gengxinneirong= (TextView) convertView.findViewById(R.id.gengxinneirong);
            holder.gengxinneirong.setText(list.get(position).toString());

            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}

class ViewHolder{
    public TextView gengxinneirong;
}


