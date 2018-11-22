package com.xhy.xhyapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ProductDetailBean123;
import com.xhy.xhyapp.purchaseactivity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class MyGridViewLeft extends BaseAdapter {
    private final List<ProductDetailBean123> listTypeInfo;
    private final ProductDetailActivity productDetailActivity;
    private List<String> gradeList=new ArrayList<>();
    private List<String> priceList=new ArrayList<>();

    public MyGridViewLeft(ProductDetailActivity productDetailActivity, List<ProductDetailBean123> listTypeInfos) {
        this.productDetailActivity=productDetailActivity;
        this.listTypeInfo=listTypeInfos;

            gradeList.add(listTypeInfo.get(0).getNum1());
            gradeList.add(listTypeInfo.get(0).getNum2());
            gradeList.add(listTypeInfo.get(0).getNum3());

            priceList.add(listTypeInfo.get(0).getUnitPrice1());
            priceList.add(listTypeInfo.get(0).getUnitPrice2());
            priceList.add(listTypeInfo.get(0).getUnitPrice3());

    }

    @Override
    public int getCount() {
        return gradeList.size();
    }

    @Override
    public Object getItem(int i) {
        return gradeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(productDetailActivity, R.layout.grid_item_price_left,null);
            viewHolder.tv_detail_num_grade= (TextView) view.findViewById(R.id.tv_detail_num_grade);
            viewHolder.tv_detail_num_price= (TextView) view.findViewById(R.id.tv_detail_num_price);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        if (i==0){
            viewHolder.tv_detail_num_grade.setText(gradeList.get(0)+"件起批");
            viewHolder.tv_detail_num_price.setText(priceList.get(0)+"/件");
        }else if(i==1){
            viewHolder.tv_detail_num_grade.setText(gradeList.get(1)+"-"+gradeList.get(2)+"件");
            viewHolder.tv_detail_num_price.setText(priceList.get(1)+"/件");
        }else if(i==2){
            viewHolder.tv_detail_num_grade.setText(gradeList.get(2)+"件起批");
            viewHolder.tv_detail_num_price.setText(priceList.get(2)+"/件");
        }
        return view;
    }

   class ViewHolder{
        public TextView tv_detail_num_grade,tv_detail_num_price;
    }
}
