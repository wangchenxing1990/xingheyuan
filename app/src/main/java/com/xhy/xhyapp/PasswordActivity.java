package com.xhy.xhyapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button, button1, getnumber;
    TimeCount timeCount;
    private EditText number, yanzhengnumber, newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        button = ((Button) findViewById(R.id.rg_pass_back));
        button.setOnClickListener(this);
        button1 = ((Button) findViewById(R.id.rg_pass_tijiao));
        button1.setOnClickListener(this);
        getnumber = ((Button) findViewById(R.id.password_getnumber));
        getnumber.setOnClickListener(this);

        timeCount = new TimeCount(60000, 1000);
        number = ((EditText) findViewById(R.id.password_number));
        yanzhengnumber = ((EditText) findViewById(R.id.password_yanzhengnummber));
        newpassword = ((EditText) findViewById(R.id.password_newpassword));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rg_pass_back:
                startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.rg_pass_tijiao:
                String num = number.getText().toString().trim();
                String yannum = yanzhengnumber.getText().toString().trim();
                String newnum = newpassword.getText().toString().trim();
                String path = "http://139.196.234.104:8000/appapi/Merchant/FindPwd?mob=" + num + "&number=" + yannum + "&pwd=" + newnum;
                System.out.println("-----------------" + path);
                if (isNumber(num)) {
                    HttpUtils httputils = new HttpUtils();
                    httputils.send(HttpRequest.HttpMethod.POST, path, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String resultq = responseInfo.result;
                            System.out.println("------------" + resultq);
                            try {
                                JSONObject json = new JSONObject(resultq);
                                String getnumberstate = json.getString("state");
                                String msg = json.getString("msg");
                                if ("0".equals(getnumberstate)) {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                                    finish();
                                } else if ("1".equals(getnumberstate)) {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                } else if ("2".equals(getnumberstate)) {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "修改异常！", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(getBaseContext(), "发送失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.password_getnumber:
                String numm = number.getText().toString().trim();
                if (isNumber(numm)) {
                    timeCount.start();
                    HttpUtils httputils = new HttpUtils();
                    httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Other/SendSms?type=1&mob=" + numm + "&merchantId=0", new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String resultq = responseInfo.result;
                            System.out.println("--------------" + resultq);
                            try {
                                JSONObject json = new JSONObject(resultq);
                                String getnumberstate = json.getString("state");
                                String msg = json.getString("msg");
                                if ("0".equals(getnumberstate)) {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(getBaseContext(), "发送失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            getnumber.setText((l / 1000) + "");
            getnumber.setClickable(true);
        }

        @Override
        public void onFinish() {
            getnumber.setText("获取验证码");
            getnumber.setClickable(false);
        }
    }

    //*
    //判断手机号号段
    // */
    private boolean isNumber(String sj) {
        if (Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(sj).matches()) {
            return true;
        }
        Toast.makeText(getBaseContext(), "请输入正确手机号！", Toast.LENGTH_SHORT).show();
        return false;
    }
}
