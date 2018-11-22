package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ApplyRecordAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ApplyRecordActivity extends AppCompatActivity {


    private List<ApplyRecord> list=new ArrayList();
    public ListView  applyrecord;
    public ImageView imageView;
    public ApplyRecord applyRecord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyrecord);
       init();
        imageView=(ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void init(){

        applyrecord=(ListView) findViewById(R.id.applyrecord);
        list.clear();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Merchant/GetMerchantLoan?merchantId=1", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    JSONArray jsonArray = jsonObject.getJSONArray("loanList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        applyRecord = new ApplyRecord();
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        applyRecord.setHuankuanbaifenbi(jsonObject1.getString("Rate"));
                        applyRecord.setHuankuanzonge(jsonObject1.getString("principalMoney"));
                        applyRecord.setJiekuanriqi(jsonObject1.getString("addTime"));
                        applyRecord.setState(jsonObject1.getString("state"));
                        applyRecord.setMerchantLoanid(jsonObject1.getString("merchantLoanid"));

                        list.add(applyRecord);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ApplyRecordAdapter applyRecordAdapter=new ApplyRecordAdapter(ApplyRecordActivity.this,list);
                applyrecord.setAdapter(applyRecordAdapter);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("--------------------错误");
                Toast.makeText(ApplyRecordActivity.this,"错误",Toast.LENGTH_SHORT).show();
            }


        });


    }


}
