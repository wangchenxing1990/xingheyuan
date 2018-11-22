package com.xhy.xhyapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhy.xhyapp.R;

import com.xhy.xhyapp.myactivity.Servicecenter;



import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CommonProblemAdapter extends BaseAdapter {

    OnBButtonClick text_button_commonproblem;
    private final FragmentActivity activity;
    private final List<Servicecenter> images;

    public CommonProblemAdapter(FragmentActivity activity, List<Servicecenter> images) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null) {
            holder=new ViewHolder();
            view=View.inflate(activity, R.layout.commonproblem_item, null);
            holder.iv_commonproblem_tiaozhuan= (ImageView) view.findViewById(R.id.iv_commonproblem_tiaozhuan);
            holder.text_commonproblem = (TextView) view.findViewById(R.id.text_commonproblem);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }


        holder.text_commonproblem.setText(images.get(i).getArticleTitle());
        holder.text_commonproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    Uri uri = Uri.parse(images.get(i).getUrl());
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    activity.startActivity(intent);


//                    Intent intent = new Intent();
//                    intent.setData(Uri.parse(images.get(i).toString()));//Url 就是你要打开的网址
//                    intent.setAction(Intent.ACTION_VIEW);
//                    activity.startActivity(intent);
            }
        });
        return view;

    }


    class ViewHolder{
        public ImageView iv_commonproblem_tiaozhuan;
        public TextView text_commonproblem;
    }

    //button回调接口
    public interface OnBButtonClick{//button点击回调接口
        public void onClick(View view);
    }
    public OnBButtonClick getOnButtonClick(){
        return text_button_commonproblem;
    }
    public void setOnButtonClick(OnBButtonClick onBButtonClick){
        this.text_button_commonproblem = onBButtonClick;
    }

}