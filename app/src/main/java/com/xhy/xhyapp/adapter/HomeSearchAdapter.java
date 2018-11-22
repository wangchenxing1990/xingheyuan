package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CategoryList;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class HomeSearchAdapter extends BaseAdapter{
    Context context;
    private  List<CategoryList> cateListData;

    public HomeSearchAdapter(Context context, List<CategoryList> cateListData) {
        this.context = context;
        this.cateListData = cateListData;
    }

    @Override
    public int getCount() {
        return cateListData.size();
    }

    @Override
    public Object getItem(int i) {
        return cateListData.get(i);
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
            view = View.inflate(context, R.layout.homesearch_item02, null);

            holder.iv_inport = (ImageView) view.findViewById(R.id.iv_inport);
            holder.tv_inport = (TextView) view.findViewById(R.id.tv_inport);
            holder.rl_image_start = (RelativeLayout) view.findViewById(R.id.rl_image_start);
            holder.checkbox_inport_start = (CheckBox) view.findViewById(R.id.checkbox_inport_start);
            // holder.checkbox_inport_start = (CheckBox) view.findViewById(R.id.checkbox_inport_start);
            holder.tv_inport_weight = (TextView) view.findViewById(R.id.tv_inport_weight);
            holder.tv_inport_stock = (TextView) view.findViewById(R.id.tv_inport_stock);
            holder.tv_inport_price = (TextView) view.findViewById(R.id.tv_inport_price);

            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(holder.iv_inport, cateListData.get(i).getThumbnailImg());
        holder.tv_inport.setText(cateListData.get(i).getGoodsName());
        holder.tv_inport_weight.setText(cateListData.get(i).getMinNum() + "件起批");
        holder.tv_inport_stock.setText("库存" + cateListData.get(i).getSumStock() + "吨");
        holder.tv_inport_price.setText("￥" + cateListData.get(i).getMinPrice());
        return view;
    }
    class ViewHolder {

        public ImageView iv_inport;
        public CheckBox checkbox_inport_start;
        public TextView tv_inport, tv_inport_weight, tv_inport_stock, tv_inport_price;
        private RelativeLayout rl_image_start;
    }
}
