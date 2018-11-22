package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/4.
 */
public class EditAddActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_my_sure;
    ImageView iv_my_back;
    private List<String> titles=new ArrayList<>();
    private List<String> titless=new ArrayList<>();


    private EditText et_name, et_phone, et_sheng, et_city, et_state, et_detail_msg;

    private String name, phone, province, city, message, state;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        initView();


    }

    private void initView() {

        iv_my_back= (ImageView) findViewById(R.id.iv_my_back);
        tv_my_sure= (TextView) findViewById(R.id.tv_my_sure);

        et_name= (EditText) findViewById(R.id.et_name);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_sheng= (EditText) findViewById(R.id.et_sheng);
        et_city= (EditText) findViewById(R.id.et_city);
        et_state= (EditText) findViewById(R.id.et_state);
        et_detail_msg= (EditText) findViewById(R.id.et_detail_msg);

        iv_my_back.setOnClickListener(this);
        tv_my_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.iv_my_back:
                onBackPressed();
                break;
            case R.id.tv_my_sure:

               // Toast.makeText(this,"点击确认按钮",Toast.LENGTH_SHORT).show();

                getData();
                requestData();

                break;

        }
    }

    private void requestData() {
        name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(EditAddActivity.this, "请输入收货人的姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(EditAddActivity.this, "请输入收货人的联系方式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(phone).matches()) {
            Toast.makeText(EditAddActivity.this, "请输入正确的联系方式", Toast.LENGTH_SHORT).show();
            return;
        }

        province = et_sheng.getText().toString().trim();
        city = et_city.getText().toString().trim();

        state = et_state.getText().toString().trim();
        message = et_detail_msg.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(EditAddActivity.this, "请输入收货人的详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void getData() {

        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addQueryStringParameter("MerchantId", "1");
        requestParams.addQueryStringParameter("addressId", "2");
        requestParams.addQueryStringParameter("address", message);

        requestParams.addQueryStringParameter("consignee", name);
        requestParams.addQueryStringParameter("isDefault", "1");
        requestParams.addQueryStringParameter("tel", phone);

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.EDIT_ADDRESS_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.print("修改地址上传数据成功" + json);
                JSONObject jsonObject = JSON.parseObject(json);
                String state = String.valueOf(jsonObject.get("state"));
                if (TextUtils.equals("0", state)) {
                    Toast.makeText(EditAddActivity.this, "修改地址成功", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent();
                    intent.putExtra("name",name);
                    intent.putExtra("phone",phone);
                    intent.putExtra("province",province);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("message",message);
                    setResult(2,intent);
                    finish();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                System.out.print("修改地址上传数据失败" + msg);
            }
        });

    }
}
