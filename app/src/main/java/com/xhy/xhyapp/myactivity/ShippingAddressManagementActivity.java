package com.xhy.xhyapp.myactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ShippingAddressManagementAdapter;
import com.xhy.xhyapp.bean.AddressBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddressManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private Button addaddress_btn;
    private ListView listview_address;
    List<AddressBean> list = new ArrayList<>();
    ;
    AddressBean addressBean;
    Context context;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_management);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        listview_address = ((ListView) findViewById(R.id.listview_address));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        ShippingAddressManagementAdapter shippingAddressManagementAdapter = new ShippingAddressManagementAdapter(ShippingAddressManagementActivity.this,list);
                        listview_address.setAdapter(shippingAddressManagementAdapter);
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/Merchant/GetMerchantAddress?merchantId=1",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    JSONArray jsonArray = json.getJSONArray("addressList");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String consignee = jsonObject.getString("consignee");
                                        String tel = jsonObject.getString("tel");
                                        String address = jsonObject.getString("address");
                                        String addressId = jsonObject.getString("addressId");

                                        addressBean = new AddressBean();
                                        addressBean.setAddress(address);
                                        addressBean.setConsignee(consignee);
                                        addressBean.setTel(tel);
                                        addressBean.setAddressId(addressId);

                                        list.add(addressBean);

                                    }

                                    Message me = new Message();
                                    me.what = 0;
                                    handler.sendMessage(me);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Toast.makeText(context, "操作失败!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).start();
        addaddress_btn = ((Button) findViewById(R.id.addaddress_btn));
        addaddress_btn.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.addaddress_btn:
                Intent intent = new Intent(ShippingAddressManagementActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
