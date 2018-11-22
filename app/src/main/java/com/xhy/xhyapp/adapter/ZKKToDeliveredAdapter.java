package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.xhy.xhyapp.myactivity.DeliveredDetailActivity;
import com.xhy.xhyapp.myactivity.VariousOrdersActivity;
import com.xhy.xhyapp.storeactivity.LogisticsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ZKKToDeliveredAdapter extends BaseAdapter{

    private BitmapUtils bitmap;
    private final FragmentActivity activity;
    private final List<VariousOrdersActivity> images;
    private int order;
    public ZKKToDeliveredAdapter(FragmentActivity activity, List<VariousOrdersActivity> images) {

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
//        if (view==null) {
        holder=new ViewHolder();
        view=View.inflate(activity, R.layout.zkktodelivered_item, null);
        holder.thumbnailimg= (ImageView) view.findViewById(R.id.imageView3);
        holder.rl_frist=(RelativeLayout) view.findViewById(R.id.rl_first);

        holder.goodsname=(TextView) view.findViewById(R.id.textView12);
        holder.expressprice=(TextView) view.findViewById(R.id.yunfei);
        holder.goodsnumber=(TextView) view.findViewById(R.id.shuliang);
        holder.iscondirm=(TextView) view.findViewById(R.id.text_sure);
        holder.thumbnailimg=(ImageView) view.findViewById(R.id.imageView3);
        holder.totalmoney=(TextView) view.findViewById(R.id.zongjia);
        holder.unitprice=(TextView) view.findViewById(R.id.textView13);
        holder.tixingfahuo=(TextView) view.findViewById(R.id.text_tixingfahuo);
        final ViewHolder viewHolder;
        viewHolder=holder;

        if ("False".equals(images.get(i).getIsCondirm())){
            holder.tixingfahuo.setText("提醒发货");
        }else {
            holder.tixingfahuo.setText("已提醒");
        }

        holder.tixingfahuo.setOnClickListener(new TextOclick(i) {
            public void onClick(View view) {

                String ApiUrl = "http://139.196.234.104:8000/appapi/MerchantOrder/RemindMerchant?merchantId=1&orderId="+images.get(i).getOrderId();
                // Toast.makeText(activity,Integer.toString(images.get(i).getOrderId()),Toast.LENGTH_SHORT).show();
                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST, ApiUrl,  new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String strings=responseInfo.result;
                        try {
                            JSONObject jsonObject=new JSONObject(strings);
                            String state=jsonObject.getString("state");
                            if("0".equals(state)){
                                viewHolder.tixingfahuo.setText("已提醒");
                            }else {

                                Toast.makeText(activity,"提醒失败",Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    @Override
                    public void onFailure(HttpException error, String msg){
                        return;
                    }
                });



            }


//                @Override
//                public void onClick(View view) {
//                    viewHolder.tixingfahuo.setText("已提醒");
//                    String ApiUrl = "http://139.196.234.104:8000/appapi/MerchantOrder/RemindMerchant?merchantId=1&orderId="+order;
//                    HttpUtils http = new HttpUtils();
//                    http.send(HttpRequest.HttpMethod.POST, ApiUrl,  new RequestCallBack<String>() {
//                        @Override
//                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                            String strings=responseInfo.result;
//                            try {
//                                JSONObject jsonObject=new JSONObject(strings);
//                                String state=jsonObject.getString("state");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            return;
//                        }
//                        @Override
//                        public void onFailure(HttpException error, String msg){
//                            return;
//                        }
//                    });
            //  }
        });
        //           view.setTag(holder);
//        }else{
//            holder= (ViewHolder) view.getTag();
//        }
//        holder.rl_frist.setOnClickListener(new View.OnClickListener() {
//            @Override
//           public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setClass(activity, DeliveredDetailActivity.class);
//                activity.startActivity(intent);
//           }
//       });

        //      order=images.get(i).getOrderId();
        //      System.out.println("---------------------------1");
        //      if (images.get(i).getIsRemind().equals("False")) {
        //           System.out.println("---------------------------2");
        //          viewHolder=holder;
//            holder.tixingfahuo.setOnClickListener(new TextOclick(i) {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("---------------------------3");
//              viewHolder.tixingfahuo.setText("已提醒");
//                    String ApiUrl = "http://139.196.234.104:8000/appapi/MerchantOrder/RemindMerchant?merchantId=1&orderId="+order;
//                    HttpUtils http = new HttpUtils();
//                    http.send(HttpRequest.HttpMethod.POST, ApiUrl,  new RequestCallBack<String>() {
//                        @Override
//                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                            String strings=responseInfo.result;
//                            try {
//                                JSONObject jsonObject=new JSONObject(strings);
//                                String state=jsonObject.getString("state");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            return;
//                        }
//                        @Override
//                        public void onFailure(HttpException error, String msg){
//                            return;
//                        }
//                    });
//                }
//});

        //              }



        holder.unitprice.setText(images.get(i).getUnitPrice());
        holder.totalmoney.setText(images.get(i).getTotalMoney());
        holder.goodsnumber.setText(images.get(i).getGoodsNumber());
        holder.expressprice.setText(images.get(i).getExpressPrice());
        holder.goodsname.setText(images.get(i).getGoodsName());


        bitmap= BitMapHelp.getBitmapUtils(this.activity);    //创建BitmapUtils对象，通过xUtils框架获取
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
        private TextView  tixingfahuo;

        private RelativeLayout rl_frist;
    }

}
