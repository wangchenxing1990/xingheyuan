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
public class MyGridViewRight extends BaseAdapter {
    private final ProductDetailActivity productDetailActivity;
    private final List<ProductDetailBean123> listTypeInfo;

    private List<String> gradeList1=new ArrayList<>();
    private List<String> priceList1=new ArrayList<>();

    public MyGridViewRight(ProductDetailActivity productDetailActivity, List<ProductDetailBean123> listTypeInfo) {
        this.productDetailActivity=productDetailActivity;
        this.listTypeInfo=listTypeInfo;


            gradeList1.add(listTypeInfo.get(1).getNum1());
            gradeList1.add(listTypeInfo.get(1).getNum2());
            gradeList1.add(listTypeInfo.get(1).getNum3());

            priceList1.add(listTypeInfo.get(1).getUnitPrice1());
            priceList1.add(listTypeInfo.get(1).getUnitPrice2());
            priceList1.add(listTypeInfo.get(1).getUnitPrice3());



    }

    @Override
    public int getCount() {
        return priceList1.size();
    }

    @Override
    public Object getItem(int i) {
        return priceList1.get(i);
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
            view=View.inflate(productDetailActivity, R.layout.grid_item_price_right,null);
            viewHolder.tv_detail_num_grade= (TextView) view.findViewById(R.id.tv_detail_num_grade);
            viewHolder.tv_detail_num_price= (TextView) view.findViewById(R.id.tv_detail_num_price);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        if (i==0){
            viewHolder.tv_detail_num_grade.setText(gradeList1.get(0)+"件起批");
            viewHolder.tv_detail_num_price.setText(priceList1.get(0)+"/件");
        }else if(i==1){
            viewHolder.tv_detail_num_grade.setText(gradeList1.get(1)+"-"+gradeList1.get(2)+"件");
            viewHolder.tv_detail_num_price.setText(priceList1.get(1)+"/件");
        }else if(i==2){
            viewHolder.tv_detail_num_grade.setText(gradeList1.get(2)+"件起批");
            viewHolder.tv_detail_num_price.setText(priceList1.get(2)+"/件");
        }
        return view;
    }

    class ViewHolder{
        public TextView tv_detail_num_grade,tv_detail_num_price;
    }
}
