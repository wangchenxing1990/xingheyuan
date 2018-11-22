package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myfragment.GeRenFragment;
import com.xhy.xhyapp.myfragment.GongSiFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class KeHuZiLiaoActivity extends AppCompatActivity{

    private LinearLayout ll_back_kehu;
    Handler handler1;
    String MerchantId ;
    String merchantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ke_hu_zi_liao);

        Intent it1 = getIntent();
        MerchantId = it1.getStringExtra("MerchantId");

        ll_back_kehu = ((LinearLayout) findViewById(R.id.ll_back_kehu));
        ll_back_kehu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler1 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        if ("1".equals(MerchantId)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.famlayout_kehu,new GongSiFragment()).commit();
                            System.out.println("--------------------kh-----3");
                        }else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.famlayout_kehu,new GeRenFragment()).commit();
                            System.out.println("--------------------kh-----4");
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "http://139.196.234.104:8000/appapi/Merchant/GetMerchantInfo?MerchantId="+MerchantId;
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, path1, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result1 = responseInfo.result;
                        System.out.println("---------------------------------"+result1);
                        try {
                            JSONObject json1 = new JSONObject(result1);
                            merchantType = json1.getString("merchantType");
                            Message me1 = new Message();
                            me1.what = 0;
                            handler1.sendMessage(me1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getBaseContext(),"信息获取失败！",Toast.LENGTH_SHORT).show();
                        System.out.println("--------------------kh-----1");
                    }
                });
            }
        }).start();
    }
}
