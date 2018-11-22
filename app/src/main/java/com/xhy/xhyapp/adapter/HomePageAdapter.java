package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.HomepageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class HomePageAdapter extends BaseAdapter {
    Context context;
    List<HomepageBean> homepageBeen;
    BitmapUtils bitmapUtils;

    public HomePageAdapter(Context context, List<HomepageBean> homepageBeen) {
        this.context = context;
        this.homepageBeen = homepageBeen;
    }

    @Override
    public int getCount() {
        return homepageBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return homepageBeen.get(i);
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
            view=View.inflate(context, R.layout.listview_home, null);
//            holder.txt_name= ((TextView) view.findViewById(R.id.textView35));
//            holder.txt_jieshao= ((TextView) view.findViewById(R.id.textView36));
//            holder.txt_price= ((TextView) view.findViewById(R.id.txt_price));
            holder.image_listhome=((ImageView) view.findViewById(R.id.image_listhome));
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(holder.image_listhome, homepageBeen.get(i).getImage());
        //holder.image_complete.setImageResource(list.get(i));
//        holder.txt_price.setText(homepageBeen.get(i).getPrice());
//        holder.txt_jieshao.setText(homepageBeen.get(i).getJieshao());
//        holder.txt_name.setText(homepageBeen.get(i).getName());
        return view;
    }
    class ViewHolder{
        ImageView image_listhome;
        TextView txt_name,txt_jieshao,txt_price;
    }
}
