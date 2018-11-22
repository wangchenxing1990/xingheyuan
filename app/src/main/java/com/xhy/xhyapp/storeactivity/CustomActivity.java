package com.xhy.xhyapp.storeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.CustomManagerAdapter;
import com.xhy.xhyapp.adapter.SwipeAdapter;
import com.xhy.xhyapp.adapter.SwipeListView;
import com.xhy.xhyapp.adapter.WXMessage;
import com.xhy.xhyapp.bean.CustomerManagementBean;
import com.xhy.xhyapp.view.MyCusPopuwindown;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity implements View.OnClickListener {


    private List<WXMessage> data = new ArrayList<WXMessage>();
    private SwipeListView mListView;
    private SwipeAdapter mAdapter;
    String QQ, wechert, phone;
    private MyCusPopuwindown myAddcusPopu;
    private ImageView iv_cus_manager_back, iv_add_customer;
    private LinearLayout ll_back;
    CustomerManagementBean customerManagementBean = null;
    List<CustomerManagementBean> list = new ArrayList<>();
    CustomManagerAdapter customManagerAdapter;
    WXMessage msg = null;
    private List<WXMessage> wmsgList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_manager);
        initView();
        initData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.GET,
                        "http://139.196.234.104:8000/appapi/Merchant/GetCustomServiceInfo?MerchantId=1",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    String state = json.getString("state");
                                    JSONArray customServiceList = json.getJSONArray("customServiceList");
                                    for (int i = 0; i < customServiceList.length(); i++) {
                                        JSONObject json01 = customServiceList.getJSONObject(i);
                                        String customerId = json01.getString("customerId");
                                        String type = json01.getString("type");
                                        String value = json01.getString("value");
                                        System.out.println("-----------008"+"---------"+type);
                                        customerManagementBean = new CustomerManagementBean();
                                        customerManagementBean.setCustomerId(customerId);
                                        customerManagementBean.setCustomerId(type);
                                        customerManagementBean.setCustomerId(value);
                                        list.add(customerManagementBean);
                                        if ("0".equals(type)) {
                                            msg = new WXMessage("QQ", QQ);
                                            msg.setIcon_id(R.drawable.qq);
                                        }else if("1".equals(type)){
                                            msg = new WXMessage("微信", wechert);
                                            msg.setIcon_id(R.drawable.wechart);
                                        }else if("2".equals(type)){
                                            msg = new WXMessage("电话", phone);
                                            msg.setIcon_id(R.drawable.call);
                                        }
                                        wmsgList.add(msg);
                                        customManagerAdapter = new CustomManagerAdapter(CustomActivity.this, list, wmsgList,mListView.getRightViewWidth());
                                        mListView.setAdapter(customManagerAdapter);
                                        customManagerAdapter.setOnRightItemClickListener(new CustomManagerAdapter.onRightItemClickListener() {
                                            @Override
                                            public void onRightItemClick(View v, final int position) {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        HttpUtils httpUtils1 = new HttpUtils();
                                                        httpUtils1.send(HttpRequest.HttpMethod.POST,
                                                                "http://139.196.234.104:8000/appapi/Merchant/CustomerServiceDel?MerchantId=1&customerId=1" + position,
                                                                new RequestCallBack<String>() {
                                                                    @Override
                                                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                                                        String result = responseInfo.result;
                                                                        try {
                                                                            JSONObject json = new JSONObject(result);
                                                                            String state = json.getString("state");
//                                                                            String type = json.getString("type");
//                                                                            System.out.println("-------------0010"+"---------"+type);
                                                                            switch (state){
                                                                                case "1":
                                                                                    mListView.deleteItem(mListView.getChildAt(position));
                                                                                    list.remove(position);
                                                                                    customManagerAdapter.notifyDataSetChanged();
                                                                                    //mAdapter.notifyDataSetChanged();
                                                                                   // CustomToast.showToast(CustomActivity.this, "删除第  " + (position + 1) + " 对话记录", 1000);
                                                                                    Toast.makeText(CustomActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                                                    break;
                                                                                default:
                                                                                    Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                                                                    break;
                                                                            }
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(HttpException error, String msg) {
                                                                        Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                }).start();

                                            }
                                        });
                                  }

                                    switch (state) {
                                        case "0":
                                            Toast.makeText(CustomActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).start();
    }



    private void initData() {
    for(int i=0;i<50;i++){
        WXMessage msg = null;
            if(i%3==0){
        msg = new WXMessage("电话客服", "155555555");
        msg.setIcon_id(R.drawable.call);
          }else if(i%3==1){
        msg = new WXMessage("QQ", "1764558056");
        msg.setIcon_id(R.drawable.qq);
         }else{
        msg = new WXMessage("微信", "4126595215648");
        msg.setIcon_id(R.drawable.wechart);
           }
        data.add(msg);
          }
    }


    /**
     * 初始化界面
     */
    private void initView() {

        //iv_cus_manager_back= (ImageView) findViewById(R.id.iv_cus_manager_back);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        iv_add_customer = (ImageView) findViewById(R.id.iv_add_customer);
        // mListView = (SwipeListView)findViewById(R.id.lv_customer_manager);
        //点击返回上一个页面
        ll_back.setOnClickListener(this);
        //点击添加客服
        iv_add_customer.setOnClickListener(this);
        mListView = (SwipeListView) findViewById(R.id.lv_customer_manager);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myAddcusPopu != null && myAddcusPopu.isShowing()) {
            myAddcusPopu.dismiss();
            myAddcusPopu = null;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.iv_add_customer:
                displayPopuwindown();
                break;
        }
    }

    private TextView tv_add_qq, tv_add_wechart, tv_add_call;
    private EditText ed_input_QQ, ed_input_wechert, ed_input_phone;
    private TextView tv_add_customer_cancel, tv_add_customer_sure;
    private int icons;
    int type;

    /**
     * 展示popuwindown
     */
    private void displayPopuwindown() {
        myAddcusPopu = new MyCusPopuwindown(CustomActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAddcusPopu.dismiss();
            }
        }, R.layout.popu_add_customer);

        myAddcusPopu.showAtLocation(CustomActivity.this.findViewById(R.id.add_popuwindownss), Gravity.CENTER, 0, 0);

        View addView = myAddcusPopu.getView();

        tv_add_qq = (TextView) addView.findViewById(R.id.tv_add_qq);
        tv_add_wechart = (TextView) addView.findViewById(R.id.tv_add_wechart);
        tv_add_call = (TextView) addView.findViewById(R.id.tv_add_call);

        ed_input_QQ = (EditText) addView.findViewById(R.id.ed_input_QQ);
        ed_input_wechert = (EditText) addView.findViewById(R.id.ed_input_wechert);
        ed_input_phone = (EditText) addView.findViewById(R.id.ed_input_phone);

        tv_add_customer_cancel = (TextView) addView.findViewById(R.id.tv_add_customer_cancel);
        tv_add_customer_sure = (TextView) addView.findViewById(R.id.tv_add_customer_sure1);

        //添加QQ
        tv_add_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_add_qq.setBackgroundResource(R.drawable.tanchaung_green_left);
                tv_add_wechart.setBackgroundResource(R.drawable.white_middle);
                tv_add_call.setBackgroundResource(R.drawable.white_right);

                ed_input_QQ.setVisibility(View.VISIBLE);
                ed_input_wechert.setVisibility(View.GONE);
                ed_input_phone.setVisibility(View.GONE);

                icons = 0;
            }
        });
        //添加微信
        tv_add_wechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_add_qq.setBackgroundResource(R.drawable.white_left);
                tv_add_wechart.setBackgroundResource(R.drawable.tanchuang_green_middle);
                tv_add_call.setBackgroundResource(R.drawable.white_right);

                ed_input_QQ.setVisibility(View.GONE);
                ed_input_wechert.setVisibility(View.VISIBLE);
                ed_input_phone.setVisibility(View.GONE);
                icons = 1;

            }
        });
        //添加电话号码
        tv_add_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_add_qq.setBackgroundResource(R.drawable.white_left);
                tv_add_wechart.setBackgroundResource(R.drawable.white_middle);
                tv_add_call.setBackgroundResource(R.drawable.tanchuang_green_right);

                ed_input_QQ.setVisibility(View.GONE);
                ed_input_wechert.setVisibility(View.GONE);
                ed_input_phone.setVisibility(View.VISIBLE);
                icons = 2;
            }
        });

        //确定
        tv_add_customer_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // initData();

//                if (icons==0){
//                    msg = new WXMessage("QQ", number);
//                    msg.setIcon_id(R.drawable.qq);
//                }else if ()
                switch (icons) {
                    case 0:
                        QQ = ed_input_QQ.getText().toString().trim();
                        //customManagerAdapter.notifyDataSetChanged();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils httpUtils = new HttpUtils();
                                RequestParams request = new RequestParams();
                                request.addQueryStringParameter("merchantId", "");
                                httpUtils.send(HttpRequest.HttpMethod.POST,
                                        "http://139.196.234.104:8000/appapi/Merchant/CustomerServiceAdd?merchantId=1&type=" + icons + "&value=" + QQ,
                                        new RequestCallBack<String>() {
                                            @Override
                                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                                String result1 = responseInfo.result;
                                                try {
                                                    JSONObject jsonObject = new JSONObject(result1);
                                                    String state = jsonObject.getString("state");
                                                    switch (state) {
                                                        case "0":
                                                            if (QQ.length() < 5) {
                                                                Toast.makeText(CustomActivity.this, "请输入QQ号", Toast.LENGTH_SHORT).show();
                                                                return;
                                                            } else {
                                                                msg = new WXMessage("QQ", QQ);
                                                                msg.setIcon_id(R.drawable.qq);
                                                                wmsgList.add(msg);
                                                            }
                                                            Toast.makeText(CustomActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                                Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).start();
                        //data.add(msg);
                        break;
                    case 1:
                        wechert = ed_input_wechert.getText().toString().trim();
                        customManagerAdapter.notifyDataSetChanged();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils httpUtils = new HttpUtils();
                                RequestParams request = new RequestParams();
                                request.addQueryStringParameter("merchantId", "");
                                httpUtils.send(HttpRequest.HttpMethod.POST,
                                        "http://139.196.234.104:8000/appapi/Merchant/CustomerServiceAdd?merchantId=1&type=" + icons + "&value=" + wechert,
                                        new RequestCallBack<String>() {
                                            @Override
                                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                                String result1 = responseInfo.result;
                                                try {
                                                    JSONObject jsonObject = new JSONObject(result1);
                                                    String state = jsonObject.getString("state");
                                                    switch (state) {
                                                        case "0":
                                                            if (wechert.length() == 0) {
                                                                Toast.makeText(CustomActivity.this, "请输入微信号", Toast.LENGTH_SHORT).show();
                                                                return;
                                                            } else {
                                                                msg = new WXMessage("微信", wechert);
                                                                msg.setIcon_id(R.drawable.wechart);
                                                                wmsgList.add(msg);
                                                                customManagerAdapter.notifyDataSetChanged();;
                                                            }
                                                            Toast.makeText(CustomActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                                Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).start();

                        break;
                    case 2:
                        phone = ed_input_phone.getText().toString().trim();
                        customManagerAdapter.notifyDataSetChanged();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils httpUtils = new HttpUtils();
                                RequestParams request = new RequestParams();
                                request.addQueryStringParameter("merchantId", "");
                                httpUtils.send(HttpRequest.HttpMethod.POST,
                                        "http://139.196.234.104:8000/appapi/Merchant/CustomerServiceAdd?merchantId=1&type=" + icons + "&value=" + phone,
                                        new RequestCallBack<String>() {
                                            @Override
                                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                                String result1 = responseInfo.result;
                                                try {
                                                    JSONObject jsonObject = new JSONObject(result1);
                                                    String state = jsonObject.getString("state");
                                                    switch (state) {
                                                        case "0":
                                                            if (phone.length() != 11) {
                                                                Toast.makeText(CustomActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                                                                return;
                                                            } else {
                                                                msg = new WXMessage("电话", phone);
                                                                msg.setIcon_id(R.drawable.call);
                                                                wmsgList.add(msg);
                                                                customManagerAdapter.notifyDataSetChanged();
                                                            }
                                                            Toast.makeText(CustomActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                                Toast.makeText(CustomActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).start();
                        break;
                }
                myAddcusPopu.dismiss();
            }
        });
        //取消
        tv_add_customer_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAddcusPopu.dismiss();
            }
        });
    }
}
