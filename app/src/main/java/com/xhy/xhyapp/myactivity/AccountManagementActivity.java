package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myview.LoginPasswordPopubwindow;
import com.xhy.xhyapp.myview.NiChengPopubwindow;
import com.xhy.xhyapp.myview.PhonePopubwindow;
import com.xhy.xhyapp.myview.TouXiangPopubwindow;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class AccountManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout re_touxiang, re_nicheng, re_phone, re_password;
    TouXiangPopubwindow touXiangPopubwindow;
    private Button btn_photo;
    private Button btn_xiangce;
    private Button btn_cancel002;
    private ImageView image_touxaing;
    NiChengPopubwindow niChengPopubwindow;
    private Button btn_sure;
    private Button btn_cancel;
    private TextView txt_name;
    private EditText edit_shuru;
    PhonePopubwindow phonePopubwindow;
    private TextView edit_phone_huoqu;
    private Button btn_phone_cancel;
    private Button btn_phone_sure;
    LoginPasswordPopubwindow loginPasswordPopubwindow;
    private Button btn_loginpassword_cancel;
    private Button btn_loginpassword_sure;
    private RelativeLayout rl_loginpassword_phone;
    private RelativeLayout password_relative;
    private TextView txt_oldpassword;
    private TextView txt_phonenumber, txt_phone_number;
    private LinearLayout ll_back;
    String nicheng, shoujihao, yanzhengma, oldphonenumber;
    TimeCount timeCount;
    private EditText edit_phonenumber;
    private EditText edit_yanzhengma;
    private TextView txt_phone;
    String mobilephone;

    //添加的返回按钮
    private ImageView iv_kaidian_back;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap bitmap;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private EditText edit_phonenumber_phone;
    private EditText textView27;
    private byte[] bytesbitmap;
    boolean bitmapshangchuang = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        requestData();
        re_touxiang = ((RelativeLayout) findViewById(R.id.re_first));
        re_nicheng = ((RelativeLayout) findViewById(R.id.re_second));
        re_phone = ((RelativeLayout) findViewById(R.id.re_third));
        re_password = ((RelativeLayout) findViewById(R.id.re_fourth));
        image_touxaing = ((ImageView) findViewById(R.id.imageView_touxiang));
        txt_name = ((TextView) findViewById(R.id.txt_name));
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        txt_phone_number = ((TextView) findViewById(R.id.txt_phone_number));

        ll_back.setOnClickListener(this);
        re_touxiang.setOnClickListener(this);
        re_nicheng.setOnClickListener(this);
        re_phone.setOnClickListener(this);
        re_password.setOnClickListener(this);

    }

    private void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httputils = new HttpUtils();
                httputils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/Merchant/GetShop?merchantId=1", new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    String state = json.getString("state");
                                    String realName = json.getString("realName");
                                    String tel = json.getString("tel");
                                    String headPic=json.getString("headPic");
                                    if ("0".equals(state)) {
                                        txt_name.setText(realName);
                                        txt_phone_number.setText(tel);
                                        oldphonenumber=txt_phone_number.getText().toString();

                                        Picasso.with(AccountManagementActivity.this).load(headPic).into(image_touxaing);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {

                            }
                        });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.re_first:
                touXiangPopubwindow = new TouXiangPopubwindow(AccountManagementActivity.this, itemsOnClick, R.layout.touxiang_popubwindow);
                touXiangPopubwindow.showAtLocation(AccountManagementActivity.this.findViewById(R.id.relative_account), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view1 = touXiangPopubwindow.getView();
                btn_photo = ((Button) view1.findViewById(R.id.btn_first));
                btn_xiangce = ((Button) view1.findViewById(R.id.btn_second));
                btn_cancel = ((Button) view1.findViewById(R.id.btn_cancel_touxiang));

                btn_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        camera();
                        touXiangPopubwindow.dismiss();
                    }
                });
                btn_xiangce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        touXiangPopubwindow.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        touXiangPopubwindow.dismiss();
                    }
                });
                break;
            case R.id.re_second:
                niChengPopubwindow = new NiChengPopubwindow(AccountManagementActivity.this, itemsOnClick, R.layout.nicheng_popubwindow);
                niChengPopubwindow.showAtLocation(AccountManagementActivity.this.findViewById(R.id.relative_account), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view2 = niChengPopubwindow.getView();
                btn_sure = ((Button) view2.findViewById(R.id.button_sure));
                btn_cancel002 = ((Button) view2.findViewById(R.id.button_cancel));
                edit_shuru = ((EditText) view2.findViewById(R.id.edit_shuru));
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nicheng = edit_shuru.getText().toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils httpUtils = new HttpUtils();
                                httpUtils.send(HttpRequest.HttpMethod.POST,
                                        "http://139.196.234.104:8000/appapi/Merchant/EditNick?MerchantId=1&userName=" + nicheng,
                                        new RequestCallBack<String>() {
                                            @Override
                                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                                String result1 = responseInfo.result;
                                                try {
                                                    JSONObject json = new JSONObject(result1);
                                                    String state = json.getString("state");
                                                    switch (state) {
                                                        case "0":
                                                            if (TextUtils.isEmpty(nicheng)) {
                                                                Toast.makeText(AccountManagementActivity.this, "请输入正确位数！", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                txt_name.setText(nicheng);
                                                                niChengPopubwindow.dismiss();
                                                            }
                                                            Toast.makeText(AccountManagementActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                                Toast.makeText(AccountManagementActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).start();


                    }
                });
                btn_cancel002.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        niChengPopubwindow.dismiss();
                    }
                });
                break;
            case R.id.re_third:
                phonePopubwindow = new PhonePopubwindow(AccountManagementActivity.this, itemsOnClick, R.layout.phone_popubwindow);
                phonePopubwindow.showAtLocation(AccountManagementActivity.this.findViewById(R.id.relative_account), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view3 = phonePopubwindow.getView();
                txt_phone = ((TextView) view3.findViewById(R.id.txt_phone));
                textView27 = ((EditText) view3.findViewById(R.id.textView27));
                edit_phonenumber_phone = ((EditText) view3.findViewById(R.id.edit_phonenumber));
                txt_phone.setText(oldphonenumber);
                //验证码按钮
                edit_phone_huoqu = ((TextView) view3.findViewById(R.id.edit_phone_huoqu));
                timeCount = new TimeCount(60000, 1000);

//                //设置提示框
//                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);//获取图片资源
//                drawable.setBounds(0, 0, 72, 72);
//                textView27.setError("不能为空", drawable);
//                edit_phonenumber_phone.setError("密码为6到12位数字和字母组合");

                edit_phone_huoqu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpUtils httpUtils = new HttpUtils();
                                httpUtils.send(HttpRequest.HttpMethod.POST,
                                        "http://139.196.234.104:8000/appapi/Other/SendSms?type=3&mob=" + oldphonenumber + "&merchantId=0",
                                        new RequestCallBack<String>() {
                                            @Override
                                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                                String resultq = responseInfo.result;
                                                try {
                                                    JSONObject json = new JSONObject(resultq);
                                                    String getnumberstate = json.getString("state");
                                                    if (getnumberstate == "0") {
                                                        Toast.makeText(AccountManagementActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(AccountManagementActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(HttpException error, String msg) {
                                                Toast.makeText(AccountManagementActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).start();
                        timeCount.start();
                    }
                });

                //popubwindow中手机号输入框
                edit_phonenumber = ((EditText) view3.findViewById(R.id.edit_phonenumber));
                //Integer.parseInt(editphonenumber);
                edit_yanzhengma = ((EditText) view3.findViewById(R.id.textView27));
                yanzhengma = edit_yanzhengma.getText().toString();
                btn_phone_cancel = ((Button) view3.findViewById(R.id.btn_phone_cancel));
                btn_phone_sure = ((Button) view3.findViewById(R.id.btn_phone_sure));
                btn_phone_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //定义一个String 存储写入的手机号
                        shoujihao = edit_phonenumber.getText().toString().trim();
                        if (NumberLength(shoujihao) && isNumber(shoujihao)) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpUtils httpUtils = new HttpUtils();
                                    httpUtils.send(HttpRequest.HttpMethod.POST,
                                            "http://139.196.234.104:8000/appapi/Merchant/EditMob?merchantId=1&newMob=" + shoujihao + "&number=" + yanzhengma,
                                            new RequestCallBack<String>() {
                                                @Override
                                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                                    String result = responseInfo.result;
                                                    try {
                                                        JSONObject json = new JSONObject(result);
                                                        String state = json.getString("state");
                                                        switch (state) {
                                                            case "0":
                                                                if (TextUtils.isEmpty(shoujihao)) {
                                                                    Toast.makeText(AccountManagementActivity.this, "请输入正确位数！", Toast.LENGTH_LONG).show();
                                                                } else {
                                                                    //将手机号从popubwindow传过去
                                                                    txt_phone_number.setText(shoujihao);
                                                                    niChengPopubwindow.dismiss();
                                                                }
                                                                Toast.makeText(AccountManagementActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                            case "1":
                                                                Toast.makeText(AccountManagementActivity.this, "验证码已过期！", Toast.LENGTH_SHORT).show();
                                                            case "2":
                                                                Toast.makeText(AccountManagementActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(HttpException error, String msg) {
                                                    Toast.makeText(AccountManagementActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).start();
                        }
                    }
                });

                btn_phone_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        phonePopubwindow.dismiss();
                    }
                });

                break;
            case R.id.re_fourth:
                loginPasswordPopubwindow = new LoginPasswordPopubwindow(AccountManagementActivity.this, itemsOnClick, R.layout.login_password_popubwindow);
                loginPasswordPopubwindow.showAtLocation(AccountManagementActivity.this.findViewById(R.id.relative_account), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view4 = loginPasswordPopubwindow.getView();
                btn_loginpassword_cancel = ((Button) view4.findViewById(R.id.button_loginpassword_cancel));
                btn_loginpassword_sure = ((Button) view4.findViewById(R.id.button_loginpassword_sure));
                rl_loginpassword_phone = ((RelativeLayout) view4.findViewById(R.id.rl_loginpassword_phone));
                password_relative = ((RelativeLayout) view4.findViewById(R.id.password_relative));
                txt_oldpassword = ((TextView) view4.findViewById(R.id.textView_oldpassword));
                txt_phonenumber = ((TextView) view4.findViewById(R.id.txt_phonenumber));

                txt_oldpassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        password_relative.setVisibility(View.VISIBLE);
                        rl_loginpassword_phone.setVisibility(View.GONE);
                        txt_oldpassword.setTextColor(0xff0d2432);
                        txt_phonenumber.setTextColor(0xffa0a0a0);
                    }
                });
                txt_phonenumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rl_loginpassword_phone.setVisibility(View.VISIBLE);
                        password_relative.setVisibility(View.GONE);
                        txt_phonenumber.setTextColor(0xff0d2432);
                        txt_oldpassword.setTextColor(0xffa0a0a0);
                    }
                });

                btn_loginpassword_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginPasswordPopubwindow.dismiss();
                    }
                });
                break;
        }
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
    /*
         * 从相机获取
         */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        // startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(AccountManagementActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.image_touxaing.setImageBitmap(bitmap);
                //上传头像
                uploadbitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadbitmap(Bitmap bitmap1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytesbitmap = baos.toByteArray();
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                String url = "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("con-lenght",""+bytesbitmap.length);
                httpPost.addHeader("merchantId","1");
                httpPost.addHeader("extension",".jpg");
                httpPost.addHeader("pType","2");
                httpPost.addHeader("shopId","1");
                ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                httpPost.setEntity(se);
                try {
                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                    String st = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject jsonObject = new JSONObject(st);
                    String state = jsonObject.getString("state");
                    if ("0".equals(state)){
                        bitmapshangchuang = true;
                        Toast.makeText(getBaseContext(),"门店头像上传成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(),"门店头像上传失败！",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 剪切图片
     *
     * @param uri
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
         * 从相册获取
         */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    //为弹出窗口实现监听类
    View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            touXiangPopubwindow.dismiss();
        }
    };


    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            edit_phone_huoqu.setText((l / 1000) + "");
            edit_phone_huoqu.setClickable(false);
        }

        @Override
        public void onFinish() {
            edit_phone_huoqu.setText("获取验证码");
            edit_phone_huoqu.setClickable(true);
        }
    }
}
