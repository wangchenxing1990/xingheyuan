package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.BitMapHelp;
import com.xhy.xhyapp.myactivity.VariousOrdersActivity;
import com.xhy.xhyapp.storeactivity.LogisticsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ZKKInboundAdapter extends BaseAdapter{

    private final FragmentActivity activity;
    private List<VariousOrdersActivity> images=new ArrayList();
    private BitmapUtils bitmap;

    public ZKKInboundAdapter(FragmentActivity activity, List<VariousOrdersActivity> images) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        //if (view==null) {
        holder=new ViewHolder();
        view=View.inflate(activity, R.layout.zkkinbound_item, null);

        holder.chakanwuliu=(TextView) view.findViewById(R.id.text_chakan);
        holder.goodsname=(TextView) view.findViewById(R.id.textView12);
        holder.expressprice=(TextView) view.findViewById(R.id.yunfei);
        holder.goodsnumber=(TextView) view.findViewById(R.id.shuliang);
        holder.iscondirm=(TextView) view.findViewById(R.id.text_sure);
        holder.thumbnailimg=(ImageView) view.findViewById(R.id.imageView3);
        holder.totalmoney=(TextView) view.findViewById(R.id.zongjia);
        holder.unitprice=(TextView) view.findViewById(R.id.textView13);
        holder.querenshouhuo=(TextView) view.findViewById(R.id.text_sure);

        final ViewHolder viewHolder;
        viewHolder=holder;

        if ("False".equals(images.get(i).getIsCondirm())){
            holder.querenshouhuo.setText("确认收货");
        }else {
            holder.querenshouhuo.setText("已收货");
        }

        holder.querenshouhuo.setOnClickListener(new TextOclick(i) {
            public void onClick(View view) {

                String ApiUrl = "http://139.196.234.104:8000/appapi/MerchantOrder/ConfirmMerchantOrder?merchantId=1&orderId=" + images.get(i).getOrderId();
                // Toast.makeText(activity,Integer.toString(images.get(i).getOrderId()),Toast.LENGTH_SHORT).show();
                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST, ApiUrl, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String strings = responseInfo.result;
                        try {
                            JSONObject jsonObject = new JSONObject(strings);
                            String state = jsonObject.getString("state");
                            if ("0".equals(state)) {
                                viewHolder.querenshouhuo.setText("已收货");
                            } else {

                                Toast.makeText(activity, "提醒失败", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return;
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        return;
                    }
                });


            }
        });










//            view.setTag(holder);
//        }else{
//            holder= (ViewHolder) view.getTag();
//        }
        holder.chakanwuliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(activity, LogisticsActivity.class);
                activity.startActivity(intent);
            }
        });

        holder.unitprice.setText(images.get(i).getUnitPrice());
        holder.totalmoney.setText(images.get(i).getTotalMoney());
        holder.goodsnumber.setText(images.get(i).getGoodsNumber());
        holder.expressprice.setText(images.get(i).getExpressPrice());
        holder.goodsname.setText(images.get(i).getGoodsName());


        bitmap=BitMapHelp.getBitmapUtils(this.activity);    //创建BitmapUtils对象，通过xUtils框架获取
        bitmap.configDefaultLoadingImage(R.drawable.ic_launcher);  //设置默认图片
        // bitmap.configDefaultLoadFailedImage(R.drawable.bitmap);    //设置加载失败的默认图片
        bitmap.configDefaultBitmapConfig(Bitmap.Config.RGB_565);   //设置图片清晰度

        bitmap.display(holder.thumbnailimg, images.get(i).getThumbnailImg()); //加载图片     参数：1.ImageView 2.url


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



    class ViewHolder{
        private TextView goodsname;//商品名称  @+id/textView12
        private TextView totalmoney;//商品总金额  @+id/zongjia
        private TextView expressprice;//快递价格  @+id/yunfei
        private TextView unitprice;//商品单价   @+id/textView13
        private TextView isremind;//是否提醒发货
        private TextView iscondirm;//是否确认收货   @+id/text_sure
        private TextView goodsnumber;//商品数量   @+id/shuliang
        private ImageView thumbnailimg;//商品图片 "@+id/imageView3"
        private TextView  chakanwuliu;
        private TextView  querenshouhuo;


//        private String goodsName;//商品名称
//        private String totalMoney;//商品总金额            物流查看按钮@+id/text_chakan
//        private String expressPrice;//快递价格
//        private String unitPrice;//商品单价
//        private String isRemind;//是否提醒发货           确定按钮@+id/text_sure
//        private String isCondirm;//是否确认收货
//        private String goodsNumber;//商品数量
//        private String thumbnailImg;//商品图片

    }


}
