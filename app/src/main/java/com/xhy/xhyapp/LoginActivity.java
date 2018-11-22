package com.xhy.xhyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private TextView btn_register, btn_zhaomi;
    private EditText edit_zhanghao;
    private EditText edit_mima;
    String userName, password;
    String imei, imsiyy;
    Thread thread;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = ((Button) findViewById(R.id.btn_login));
        btn_register = ((TextView) findViewById(R.id.btn_register));
        btn_zhaomi = ((TextView) findViewById(R.id.btn_zhaomi));
        edit_zhanghao = ((EditText) findViewById(R.id.edit_zhanghao));
        edit_mima = ((EditText) findViewById(R.id.edit_mima));

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_zhaomi.setOnClickListener(this);
        //检查网络
        CheckNetworkState();
        //获取手机序列号和手机号
        TelephonyManager tm = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
        imei = tm.getDeviceId();//手机唯一标识
        imsiyy = tm.getSubscriberId();//运营商信息
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setTitle("登陆");
                mDialog.setMessage("正在登陆服务器，请稍后...");
                mDialog.show();
                userName = edit_zhanghao.getText().toString().trim();
                password = edit_mima.getText().toString().trim();
                final String path = "http://139.196.234.104:8000/appapi/Merchant/Login?account=" + userName + "&pwd=" + password + "&imsi=" + imsiyy + "&imei=" + imei + "&platForm=0";
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, path, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result1 = responseInfo.result;
                                try {
                                   JSONObject json = new JSONObject(result1);
                                    String state = json.getString("state");
                                   // String merchantState = json.getString("merchantState");
                                    switch (state) {
                                        case "0":
                                            String merchantState = json.getString("merchantState");
                                            switch (merchantState){
                                                case "0" :
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    break;
                                                case "1" :
                                                    Intent intents = new Intent(LoginActivity.this, RegisterShenActivity.class);
                                                    intents.putExtra("type","1");
                                                    intents.putExtra("reson","正在审核中 请等待...");
                                                    startActivity(intents);
                                                    mDialog.dismiss();
                                                    break;
                                                case "2" :
                                                    Intent intents1 = new Intent(LoginActivity.this, RegisterShenActivity.class);
                                                    intents1.putExtra("type","1");
                                                    intents1.putExtra("reson","审核未通过");
                                                    intents1.putExtra("result",json.getString("reason"));
                                                    startActivity(intents1);
                                                    mDialog.dismiss();
                                                    break;
                                                case "3" :
                                                    Toast.makeText(getBaseContext(),"帐号禁用！",Toast.LENGTH_SHORT).show();
                                                    mDialog.dismiss();
                                                    break;
                                                case "4" :
                                                    Intent intents3 = new Intent(LoginActivity.this, RegisterActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("userID",json.getString("merchantId"));
                                                    intents3.putExtras(bundle);
                                                    Toast.makeText(getBaseContext(),"信息待完善！",Toast.LENGTH_SHORT).show();
                                                    startActivity(intents3);
                                                    mDialog.dismiss();
                                                    break;
                                                default:
                                                    break;
                                            }
                                        default:
//                                            //Toast.makeText(LoginActivity.this, "账号或密码错误!", Toast.LENGTH_LONG).show();
//                                            Intent intentn = new Intent(LoginActivity.this, MainActivity.class);
//                                            startActivity(intentn);
//                                            finish();
                                            break;
                                        case "1":
                                            Toast.makeText(LoginActivity.this, "账号不存在，请重试", Toast.LENGTH_LONG).show();
                                            break;
                                        case "2":
                                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Toast.makeText(LoginActivity.this, "账号或密码错误!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                thread.start();

                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(LoginActivity.this, RegisteroneActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_zhaomi:
                Intent intent2 = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void CheckNetworkState() {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return;
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return;
        showTips();
    }

    private void showTips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("没有可用网络");
        builder.setMessage("当前网络不可用，是否设置网络？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 如果没有网络连接，则进入网络设置界面
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }
}
