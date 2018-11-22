package com.xhy.xhyapp.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myactivity.RepaymentHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ApplyDetailsAdapter extends BaseAdapter {

    private final FragmentActivity activity;
    public List<RepaymentHistory> list=new ArrayList();



    public ApplyDetailsAdapter(FragmentActivity activity, List<RepaymentHistory> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        //if (view==null) {
        holder=new ViewHolder();
        view=View.inflate(activity, R.layout.applydetails_item, null);

        holder.huankuancishu=(TextView) view.findViewById(R.id.huankuancishu);
        holder.huankuanriqi=(TextView) view.findViewById(R.id.huankuanriqi);
        holder.huankuanjine=(TextView) view.findViewById(R.id.huankuanjine);
        final ViewHolder viewHolder;
        viewHolder=holder;

        holder.huankuancishu.setText(list.get(i).getHuankuancishu());
        holder.huankuanriqi.setText(list.get(i).getHuankuanriqi());
        holder.huankuanjine.setText(list.get(i).getHuankuanjine());


        return view;

    }

    private class TextOclick implements View.OnClickListener {
        int i;
        public TextOclick(int i) {
            this.i=i;
        }
        @Override
        public void onClick(View view) {
        }
    }

    class ViewHolder {

        private TextView huankuancishu;//还款次数
        private TextView huankuanriqi;//还款日期
        private TextView huankuanjine;//还款金额
    }

}


