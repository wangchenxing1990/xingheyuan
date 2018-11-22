package com.xhy.xhyapp;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisteroneActivity extends AppCompatActivity {
    private Button button1,button2,getNumber;
    private Intent intent1;
    final static int AGREE = 0;
    final static int UNAGREE = 1;
    private int agree = AGREE;
    private EditText user,number,password;
    String userString,numberString,passwordString;
    TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerone);

        button1 = ((Button) findViewById(R.id.reg_one_zhuce));
        button2 = ((Button) findViewById(R.id.reg_one_agree));

        user = ((EditText) findViewById(R.id.reg_one_user));
        number = ((EditText) findViewById(R.id.reg_one_number));
        password = ((EditText) findViewById(R.id.reg_one_password));

        button1.setOnClickListener(new View.OnClickListener() {//确定注册提交按钮
            @Override
            public void onClick(View view) {
                if (agree == 0){
                    userString = user.getText().toString().trim();
                    numberString = number.getText().toString().trim();
                    passwordString = password.getText().toString().trim();

                    if (numberString.length()>0){
                        if (NumberLength(userString) && isNumber(userString)){
                            TelephonyManager tm = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
                            String imei = tm.getDeviceId();//手机唯一标识
                            String imsiyy = tm.getSubscriberId();//运营商信息
                            String path1 = "http://139.196.234.104:8000/appapi/Merchant/Regist?mob="+userString+"&pwd="+passwordString+"&number="+numberString+"&imei="+imei+"&imsi="+imsiyy+"&platForm=0";
                            HttpUtils httputil = new HttpUtils();
                            httputil.send(HttpRequest.HttpMethod.POST, path1, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String jieguo = responseInfo.result;
                                    try {
                                        JSONObject jsonObject = new JSONObject(jieguo);
                                        String statejieguo = jsonObject.getString("state");
                                        if ("0".equals(statejieguo)){
                                            Toast.makeText(getBaseContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                            Intent intent01 = new Intent();
                                            intent01.putExtra("userID",jsonObject.getString("merchantId"));
                                            intent01.setClass(RegisteroneActivity.this,RegisterActivity.class);
                                            startActivity(new Intent(intent01));
                                            finish();
                                        }else if ("1".equals(statejieguo)){
                                            Toast.makeText(getBaseContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }else if ("2".equals(statejieguo)){
                                            Toast.makeText(getBaseContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }else if ("3".equals(statejieguo)){
                                            Toast.makeText(getBaseContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getBaseContext(),"注册失败",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Toast.makeText(getBaseContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(getBaseContext(),"请输入验证码",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//同意协议
                if (agree == 1){
                    button2.setBackgroundResource(R.drawable.yuanyuan_04);
                    button1.setBackgroundResource(R.drawable.shezhi_tuichudenglu);
                    //button1.setBackgroundColor(0xff3fc199);
                    agree = AGREE;
                }else {
                    button2.setBackgroundResource(R.drawable.zhuceshouji_22);
                    //button1.setBackgroundColor(0xffd0d0d0);
                    button1.setBackgroundResource(R.drawable.noagree);
                    agree = UNAGREE;
                }
            }
        });
        ((TextView) findViewById(R.id.reg_one_xieyi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"协议暂无",Toast.LENGTH_SHORT).show();
            }
        });


        getNumber = ((Button) findViewById(R.id.reg_one_getNumber));//联网获取验证码
        timeCount = new TimeCount(60000,1000);
        getNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString  = user.getText().toString().trim();
                if (NumberLength(userString) && isNumber(userString)){
                    timeCount.start();
                    HttpUtils httputils = new HttpUtils();
                    httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Other/SendSms?type=0&mob="+userString+"&merchantId=0", new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String resultq = responseInfo.result;
                            try {
                                JSONObject json = new JSONObject(resultq);
                                String getnumberstate = json.getString("state");
                                if ("3".equals(getnumberstate)){
                                    Toast.makeText(getBaseContext(),"该帐号已经注册",Toast.LENGTH_SHORT).show();
                                }else if("0".equals(getnumberstate)){
                                    Toast.makeText(getBaseContext(),"短信已发送",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getBaseContext(),"获取验证码异常！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(getBaseContext(),"发送失败！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });
        ((Button) findViewById(R.id.rg_one_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean NumberLength(String usernamber1) {
        if (11 == usernamber1.length()){
            return true;
        }
        Toast.makeText(getBaseContext(),"请输入正确手机号！",Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isNumber(String sj) {//判断手机号号段
        if (Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(sj).matches()) {
            return true;
        }
        Toast.makeText(getBaseContext(),"请输入正确手机号！",Toast.LENGTH_SHORT).show();
        return false;
    }


    private class TimeCount extends CountDownTimer{

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l) {
            getNumber.setText((l/1000)+"");
            getNumber.setClickable(true);
        }
        @Override
        public void onFinish() {
            getNumber.setText("获取验证码");
            getNumber.setClickable(false);
        }
    }
}
