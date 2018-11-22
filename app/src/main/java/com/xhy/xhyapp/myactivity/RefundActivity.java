package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyRefundAdapter;
import com.xhy.xhyapp.bean.RefundBean;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.ArrayList;
import java.util.List;

public class RefundActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listview_refund;
    private List<Integer> images = new ArrayList<Integer>();
    private LinearLayout ll_back;

    private List<RefundBean> lists = new ArrayList<>();
    private RefundBean refundBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        requestData();
        listview_refund = ((ListView) findViewById(R.id.listview_refund));
        ll_back = ((LinearLayout) findViewById(R.id.ll_back_refund));
        ll_back.setOnClickListener(this);

    }

    private void requestData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("pageIndex", "1");
        requestParams.addQueryStringParameter("pageSize", "300");
        requestParams.addQueryStringParameter("orderState", "4");

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.GENERATE_ORDER_DETAIL_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.print("获取订单详情信息" + json);
                //解析数据
                List<RefundBean>  listss=  praseJson(json);
                MyRefundAdapter myRefundAdapter = new MyRefundAdapter(RefundActivity.this, listss);
                listview_refund.setAdapter(myRefundAdapter);

            }

            @Override
            public void onFailure(HttpException error, String msg) {

                Toast.makeText(RefundActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<RefundBean> praseJson(String json) {

        JSONObject jsonObject = JSON.parseObject(json);
        String state = jsonObject.getString("state");
        if ("0".equals(state)) {
            Toast.makeText(RefundActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("orderList");
        lists.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            refundBean = new RefundBean();
            String jsonArray1 = String.valueOf(jsonArray.get(i));
            JSONObject jsonObject1 = JSON.parseObject(jsonArray1);
            refundBean.setGoodsName(jsonObject1.getString("goodsName"));
            refundBean.setGoodsNumber(jsonObject1.getString("goodsNumber"));
            refundBean.setThumbnailImg(jsonObject1.getString("thumbnailImg"));
            refundBean.setUnitPrice(jsonObject1.getString("unitPrice"));
            refundBean.setTotalMoney(jsonObject1.getString("totalMoney"));
            refundBean.setOrderState(jsonObject1.getString("orderState"));
            lists.add(refundBean);
        }
        return lists;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_refund:
                finish();
                break;
        }
    }
}
