package com.xhy.xhyapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.RefundBean;
import com.xhy.xhyapp.myactivity.RefundActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyRefundAdapter extends BaseAdapter {
    private List<RefundBean> listss;
    RefundActivity activity;

    public MyRefundAdapter(RefundActivity refundActivity, List<RefundBean> listss) {
        this.activity = refundActivity;
        this.listss = listss;
    }

    @Override
    public int getCount() {
        return listss.size();
    }

    @Override
    public Object getItem(int i) {
        return listss.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = View.inflate(activity, R.layout.myrefund_item, null);
            holder.textView12= (TextView) view.findViewById(R.id.textView12);
            holder.image_refund = (ImageView) view.findViewById(R.id.imageView_refund);
            holder.tv_freight= (TextView) view.findViewById(R.id.tv_freight);
            holder.tv_num= (TextView) view.findViewById(R.id.tv_num);
            holder.textView13= (TextView) view.findViewById(R.id.textView13);
            holder.tv_examine= (TextView) view.findViewById(R.id.tv_examine);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BitmapUtils bitmapUtils=new BitmapUtils(activity);
        bitmapUtils.display(holder.image_refund,listss.get(i).getThumbnailImg());

        holder.textView12.setText(listss.get(i).getGoodsName());
        holder.tv_num.setText(listss.get(i).getGoodsNumber());
        holder.textView13.setText(listss.get(i).getUnitPrice());
        holder.tv_freight.setText(listss.get(i).getTotalMoney());

        if ("4".equals(listss.get(i).getOrderState())){
             holder.tv_examine.setText("申请退货中");
        }else if ("5".equals(listss.get(i).getOrderState())){
            holder.tv_examine.setText("退货失败");
        }else if("6".equals(listss.get(i).getOrderState())){
            holder.tv_examine.setText("已退货");
        }
        return view;
    }

    class ViewHolder {
        ImageView image_refund;
        TextView textView12;
        TextView tv_freight;
        TextView tv_num;
        TextView textView13;
        TextView tv_examine;
    }
}
