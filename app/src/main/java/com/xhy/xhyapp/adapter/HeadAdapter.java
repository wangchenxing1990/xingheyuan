package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xhy.xhyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class HeadAdapter extends BaseAdapter{
    List<Integer> imageviews=new ArrayList<>();
    Context context;


    public HeadAdapter(List<Integer> imageviews, Context context) {
        this.imageviews = imageviews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageviews.size();
    }

    @Override
    public Object getItem(int position) {
        return imageviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.photo_item,viewGroup,false);
            holder=new ViewHolder();
            holder.photoImage= (ImageView) convertView.findViewById(R.id.image_listhome);
            //holder.fruit_name.setText();
            //holder.fruit_weight.setText();
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
            holder.photoImage.setImageResource(imageviews.get(position));
        }
        return convertView;
    }
    class ViewHolder{
        ImageView photoImage;
    }
}
