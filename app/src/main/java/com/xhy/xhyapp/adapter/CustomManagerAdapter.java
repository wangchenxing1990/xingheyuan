package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CustomerManagementBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class CustomManagerAdapter extends BaseAdapter {

    private final List<WXMessage> msg;
    Context context;
    private int mRightWidth = 0;
    List<CustomerManagementBean> list = new ArrayList<>();

    public CustomManagerAdapter(Context context, List<CustomerManagementBean> list, List<WXMessage> msg, int mRightWidth) {
        this.context = context;
        this.msg = msg;
        this.list = list;
        this.mRightWidth = mRightWidth;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_customer, parent, false);
            holder = new ViewHolder();
            holder.item_left = (RelativeLayout) convertView.findViewById(R.id.item_left);
            holder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_cus_mag_num = (TextView) convertView.findViewById(R.id.tv_cus_mag_num);
            //holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
            convertView.setTag(holder);
        } else {// 有直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        holder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);


       //CustomerManagementBean msg = list.get(position);
        holder.tv_title.setText(msg.get(position).getTitle());
        holder.tv_cus_mag_num.setText(list.get(position).getCustomerId());
       // holder.iv_icon.setImageResource(list.get(position).getType());
        //holder.tv_title.setText(list.get(position).getCustomerId());
        holder.tv_cus_mag_num.setTextColor(0xff464646);
        //holder.tv_time.setText(msg.getTime());
        holder.iv_icon.setImageResource(msg.get(position).getIcon_id());
        holder.item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightItemClick(v, position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        RelativeLayout item_left;
        RelativeLayout item_right;

        TextView tv_title;
        TextView tv_cus_mag_num;
        //TextView tv_time;
        ImageView iv_icon;
        TextView item_right_txt;
    }

    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;
    public void setOnRightItemClickListener(onRightItemClickListener listener) {
        mListener = listener;
    }
    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
