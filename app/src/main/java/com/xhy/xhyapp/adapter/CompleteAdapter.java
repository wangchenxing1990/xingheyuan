package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CompleteBean;
import com.xhy.xhyapp.content.ContentUrl;
import com.xhy.xhyapp.helper.BitmapHelper;
import com.xhy.xhyapp.myactivity.ConfirmOrderActivity;
import com.xhy.xhyapp.myactivity.ReturnActivity;
import com.xhy.xhyapp.storeactivity.LogisticsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class CompleteAdapter extends BaseAdapter {
    Context context;
    List<CompleteBean> list;
    BitmapUtils bitmapUtils;
    private int flag;

    private List<String> listData = new ArrayList();

    public CompleteAdapter(Context context, List<CompleteBean> list) {
        this.context = context;
        this.list = list;
        bitmapUtils = BitmapHelper.init(context);
        for (int i = 0; i < list.size(); i++) {
            listData.add(list.get(i).getOrderState());
        }
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
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.complete_item, null);

            holder.txt_goodsName = ((TextView) view.findViewById(R.id.textView12));
            holder.txt_expressPrice = ((TextView) view.findViewById(R.id.textView13));
            holder.txt_unitPrice = ((TextView) view.findViewById(R.id.textView40));
            holder.txt_totalMoney = ((TextView) view.findViewById(R.id.txt_totalMoney));
            holder.goodsNumber = ((TextView) view.findViewById(R.id.textView37));
            holder.image_complete = (ImageView) view.findViewById(R.id.imageView3);
            holder.text_chakan = (TextView) view.findViewById(R.id.text_chakan);
            holder.text_sure = (TextView) view.findViewById(R.id.text_sure);
            holder.textView24 = (TextView) view.findViewById(R.id.textView24);
            holder.rl_sure = (RelativeLayout) view.findViewById(R.id.rl_sure);
            holder.rl_chakna = (RelativeLayout) view.findViewById(R.id.rl_chakna);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        switch (listData.get(i)) {
            case "1":
                holder.textView24.setText("待支付");
                holder.text_chakan.setText("取消订单");
                holder.text_sure.setText("支付");

                break;

            case "2":
                holder.textView24.setText("待发货");
                holder.text_chakan.setText("");
                holder.text_sure.setText("提醒发货");

                break;

            case "3":
                holder.textView24.setText("待收货");
                holder.text_chakan.setText("");
                holder.text_sure.setText("确认收货");
                break;

            case "4":
                holder.textView24.setText("交易完成");
                holder.text_chakan.setText("");
                holder.text_sure.setText("申请退货");
                break;
            case "5":
                holder.textView24.setText("退货失败");
                holder.text_chakan.setText("");
                holder.text_sure.setText("");
                break;
            case "6":
                holder.textView24.setText("已退货");
                holder.text_chakan.setText("");
                holder.text_sure.setText("");
                break;
            case "7":
                holder.textView24.setText("已收货");
                holder.text_chakan.setText("");
                holder.text_sure.setText("");
                break;
        }

        holder.text_chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(listData.get(i))) {
                    //取消订单
                    cancelOrder(i);
                }
            }
        });

        holder.text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(listData.get(i))) {
                    //支付
                    Intent intent = new Intent(context, ConfirmOrderActivity.class);

                    context.startActivity(intent);

                } else if ("2".equals(listData.get(i))) {
                    //提醒发货
                    remindMerchant(i);
                } else {
                    if ("3".equals(listData.get(i))) {
                        //确认收货
                        confirmMerchant(i);
                    } else {
                        if ("4".equals(listData.get(i))) {
                            //申请退货
                            Intent intent = new Intent(context, ReturnActivity.class);
                            intent.putExtra("", "");
                            intent.putExtra("", "");
                            intent.putExtra("", "");
                            intent.putExtra("", "");
                            intent.putExtra("", "");
                            context.startActivity(intent);

                        }
                    }
                }
            }
        });

        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(holder.image_complete, list.get(i).getThumbnailImg());

        holder.txt_totalMoney.setText(list.get(i).getTotalMoney());
        holder.goodsNumber.setText(list.get(i).getGoodsNumber());
        holder.txt_expressPrice.setText(list.get(i).getExpressPrice());
        holder.txt_goodsName.setText(list.get(i).getGoodsName());
        holder.txt_unitPrice.setText(list.get(i).getUnitPrice());

        return view;
    }

    //确认收货
    private void confirmMerchant(int i) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("orderId", list.get(i).getOrderId());
        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.CONFIRM_MERCHANT_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                JSONObject jsonObject = JSON.parseObject(json);
                String state = jsonObject.getString("state");
                if ("0".equals(state)) {
                    Toast.makeText(context, "已确认收货", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "确认收货失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //提醒发货
    private void remindMerchant(int i) {

        HttpUtils httpUtils = new HttpUtils();

        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("orderId", list.get(i).getOrderId());

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.REMIND_MERCHANT_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                JSONObject jsonObject = JSON.parseObject(json);
                String state = jsonObject.getString("state");
                if ("0".equals(state)) {
                    Toast.makeText(context, "已提醒发货了请耐心等待", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "已提醒发货失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelOrder(final int i) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("orderId", list.get(i).getOrderId());
        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.CANCEl_ORDER_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                JSONObject jsonObject = JSON.parseObject(json);
                String state = jsonObject.getString("state");
                if ("0".equals(state)) {
                    Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
                    list.remove(i);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "服务器异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "订单取消失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class ViewHolder {
        public RelativeLayout rl_sure,rl_chakna;
        public ImageView image_complete;
        public TextView text_chakan, text_sure, goodsNumber, txt_totalMoney, txt_goodsName, txt_expressPrice, txt_unitPrice, textView24;
    }

}
