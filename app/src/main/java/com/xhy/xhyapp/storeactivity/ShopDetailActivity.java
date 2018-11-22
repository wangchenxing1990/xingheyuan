package com.xhy.xhyapp.storeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private TextView text_realname;
    private TextView text_shopphone;
    private TextView text_shoppingfen;
    String shopname, shopphone, shoppingfen;
    private TextView txt_shopname;
    private ImageView shop_image001, shop_image002, shop_image003, shop_image004, shop_image005;
    private ImageView image_touxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        text_realname = ((TextView) findViewById(R.id.textView001));
        text_shopphone = ((TextView) findViewById(R.id.textView000));
        text_shoppingfen = ((TextView) findViewById(R.id.text_pingfen));
        txt_shopname = ((TextView) findViewById(R.id.txt_shopname));
        shop_image001 = ((ImageView) findViewById(R.id.shop_image001));
        shop_image002 = ((ImageView) findViewById(R.id.shop_image002));
        shop_image003 = ((ImageView) findViewById(R.id.shop_image003));
        shop_image004 = ((ImageView) findViewById(R.id.shop_image004));
        shop_image005 = ((ImageView) findViewById(R.id.shop_image005));
        image_touxiang = ((ImageView) findViewById(R.id.image_touxiang));

        ll_back.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/Merchant/GetShop?merchantId=1",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    String state = json.getString("state");
                                    String realName = json.getString("realName");
                                    String shopName = json.getString("shopName");
                                    String shopScore = json.getString("shopScore");
                                    String tel = json.getString("tel");
                                    String shopLevel = json.getString("shopLevel");
                                    String headPic = json.getString("headPic");

                                    switch (state) {
                                        case "0":
                                            text_realname.setText(realName);
                                            text_shopphone.setText(tel);
                                            text_shoppingfen.setText(shopScore);
                                            txt_shopname.setText(shopName);
                                            Picasso.with(ShopDetailActivity.this).load(headPic).into(image_touxiang);
                                            break;
                                        default:
                                            break;
                                    }
                                    switch (shopLevel) {
                                        case "1":
                                            shop_image001.setImageResource(R.drawable.xing_linag);
                                            break;
                                        case "2":
                                            shop_image001.setImageResource(R.drawable.xing_linag);
                                            shop_image002.setImageResource(R.drawable.xing_linag);
                                            break;
                                        case "3":
                                            shop_image001.setImageResource(R.drawable.xing_linag);
                                            shop_image002.setImageResource(R.drawable.xing_linag);
                                            shop_image003.setImageResource(R.drawable.xing_linag);
                                            break;
                                        case "4":
                                            shop_image001.setImageResource(R.drawable.xing_linag);
                                            shop_image002.setImageResource(R.drawable.xing_linag);
                                            shop_image003.setImageResource(R.drawable.xing_linag);
                                            shop_image004.setImageResource(R.drawable.xing_linag);
                                            break;
                                        case "5":
                                            shop_image001.setImageResource(R.drawable.xing_linag);
                                            shop_image002.setImageResource(R.drawable.xing_linag);
                                            shop_image003.setImageResource(R.drawable.xing_linag);
                                            shop_image004.setImageResource(R.drawable.xing_linag);
                                            shop_image005.setImageResource(R.drawable.xing_linag);
                                            break;
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
            default:
                break;
        }
    }
}