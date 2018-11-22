package com.xhy.xhyapp.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.BitMapHelp;
import com.xhy.xhyapp.bean.Category;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MyCategoryAdapter extends BaseAdapter {

    private final FragmentActivity activity;
    private final List<Category> cateDAtas;

    public MyCategoryAdapter(FragmentActivity activity, List<Category> cateDAtas) {
        this.activity = activity;
        this.cateDAtas = cateDAtas;
    }

    @Override
    public int getCount() {
        return cateDAtas.size();
    }

    @Override
    public Object getItem(int i) {
        return cateDAtas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(activity, R.layout.purchase_item, null);
            holder.iv_category_icon = (ImageView) view.findViewById(R.id.iv_category_icon);
            holder.tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BitmapUtils bitmapUtils = new BitmapUtils(activity);
        bitmapUtils.display(holder.iv_category_icon, cateDAtas.get(i).getIcon());

        holder.tv_category_name.setText(cateDAtas.get(i).getCategoryName());
        return view;
    }

    class ViewHolder {
        ImageView iv_category_icon;
        TextView tv_category_name;
    }
}
