package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.regex.Pattern;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView tv_my_save;
    private EditText et_name, et_phone, et_sheng, et_city, et_state, et_detail_msg;
    private String detail_message,city,sheng,phone,state,name,quanbudizhi;
    private RelativeLayout suozaidiqu;
    private TextView textView5,textView43,textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_my_save = (TextView) findViewById(R.id.tv_my_save);
        ll_back.setOnClickListener(this);
        tv_my_save.setOnClickListener(this);
        suozaidiqu=(RelativeLayout) findViewById(R.id.suozaidiqu);
        textView5=(TextView) findViewById(R.id.textView5);
        textView43=(TextView) findViewById(R.id.textView43);
        textView3=(TextView) findViewById(R.id.textView3);
        suozaidiqu.setOnClickListener(this);

        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
//        et_sheng = (EditText) findViewById(R.id.et_sheng);
//        et_city = (EditText) findViewById(R.id.et_city);
//        et_state = (EditText) findViewById(R.id.et_state);
        et_detail_msg = (EditText) findViewById(R.id.et_detail_msg);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_my_save:
                requestData();
                break;
            case R.id.suozaidiqu:
                showAdressDialog();
                break;
        }
    }

    private void requestData() {
        name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(phone).matches()) {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        detail_message = et_detail_msg.getText().toString().trim();
        if (TextUtils.isEmpty(detail_message)) {
            Toast.makeText(this, "请输入您的详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        quanbudizhi=textView5.getText().toString()+textView3.getText().toString()+textView43.getText().toString()+detail_message;

        if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(detail_message)){
            Toast.makeText(AddAddressActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
        }else {
            HttpUtils httpUtils = new HttpUtils();
            RequestParams requestParams = new RequestParams();
            requestParams.addQueryStringParameter("MerchantId", "1");
            requestParams.addQueryStringParameter("address", quanbudizhi);
            requestParams.addQueryStringParameter("consignee", name);
            requestParams.addQueryStringParameter("isDefault", "1");
            requestParams.addQueryStringParameter("tel", phone);

            httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.ADD_NEW_ADDRESS_URL, requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json = responseInfo.result;
                    JSONObject jsonObject = JSON.parseObject(json);
                    String state = jsonObject.getString("state");
                    if ("0".equals(state)) {

                        Toast.makeText(AddAddressActivity.this, String.valueOf(jsonObject.get("msg")), Toast.LENGTH_SHORT).show();
                        Intent it = new Intent();
                        it.putExtra("name",name);
                        it.putExtra("phone",phone);
                        it.putExtra("address",quanbudizhi);
                        setResult(2,it);
                        finish();
                        return;

                    }
                }
                @Override
                public void onFailure(HttpException error, String msg) {

                }
            });
        }
    }

    /**
     * showAdressDialog
     */
    public void showAdressDialog() {
        final AdressDialog dialog = new AdressDialog(AddAddressActivity.this).builder().setCancelable(false).setCanceledOnTouchOutside(true);
        dialog.setConfim(new AdressDialog.confirmListener() {
            @Override
            public void onClick() {
                textView5.setText(dialog.getprovince());
                textView43.setText( dialog.getAdress());
                textView3.setText(dialog.gettown());

                Toast.makeText(AddAddressActivity.this,dialog.getprovince() + dialog.getAdress() + dialog.gettown(),Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
    }
}
