package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ProductDetailBean123;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_confirm_back, rl_item_second;
    private RelativeLayout rl_union_pay, rl_zhifubao_pay, rl_wechert_pay, rl_off_line_pay;
    private ImageView image;
    private TextView tv_name_order, tv_order_stande, tv_order_number, tv_one_price, tv_one_count;
    private ImageView image01;
    private TextView tv_name_order1, tv_order_stande1, tv_order_number1, tv_second_price, tv_second_count;
    private TextView tv_confirm_pay, tv_amount_payable;
    private ImageView iv_union_pay, iv_zhifubao_pay, iv_wechart_pay, iv_off_line_pay;
    private int flag;
    private View view1;

    private String imageview;
    private String title;
    private String num;
    private String num1;
    private String num2;
    private String goodsStyleType;
    private String goodsId;
    private List<ProductDetailBean123> product;
    private HashMap<String, HashMap<String, String>[]> hashMap0 = new HashMap<String, HashMap<String, String>[]>();
    private HashMap<String, String> hashMap1 = new HashMap<String, String>();
    private HashMap<String, String> hashMap2 = new HashMap<String, String>();
    private HashMap<String, String> hashMap3 = new HashMap<String, String>();
    private ArrayList list = new ArrayList();
    private List[] lists;

    private String name;
    private String phone;
    private String address;

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private JSONObject jsonObject1;
    private JSONArray jsonArray1;
    private String generJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Intent intent = getIntent();
        goodsStyleType = intent.getStringExtra("goodsStyleType");
        imageview = intent.getStringExtra("imageview");
        title = intent.getStringExtra("title");
        num = intent.getStringExtra("num");
        num1 = intent.getStringExtra("num1");
        num2 = intent.getStringExtra("num2");
        product = (List<ProductDetailBean123>) intent.getSerializableExtra("product");

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        goodsId = intent.getStringExtra("goodsId");

        initView();

        switch (goodsStyleType) {
            case "2":
                if (Integer.parseInt(num1) == 0) {
                    view1.setVisibility(View.VISIBLE);
                    rl_item_second.setVisibility(View.VISIBLE);
                    tv_name_order.setText(title);
                    if (Integer.parseInt(product.get(0).getNum1()) <= Integer.parseInt(num1) && Integer.parseInt(num1) < Integer.parseInt(product.get(0).getNum2())) {
                        tv_one_price.setText(product.get(0).getUnitPrice3());
                        tv_order_number.setText("数量: " + num1 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice3()) * Float.parseFloat(num1)));
                    } else if (Integer.parseInt(product.get(0).getNum2()) <= Integer.parseInt(num1) && Integer.parseInt(num1) < Integer.parseInt(product.get(0).getNum3())) {
                        tv_one_price.setText(product.get(0).getUnitPrice2());
                        tv_order_number.setText("数量: " + num + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice2()) * Float.parseFloat(num1)));
                    } else if (Integer.parseInt(num1) >= Integer.parseInt(product.get(0).getNum3())) {
                        tv_one_price.setText(product.get(0).getUnitPrice2());
                        tv_order_number.setText("数量: " + num1 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num1)));
                    }
                    tv_amount_payable.setText("应付金额:" + Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num1));
                } else if (Integer.parseInt(num2) == 0) {
                    view1.setVisibility(View.VISIBLE);
                    rl_item_second.setVisibility(View.VISIBLE);
                    tv_name_order.setText(title);
                    if (Integer.parseInt(product.get(1).getNum1()) <= Integer.parseInt(num2) && Integer.parseInt(num2) < Integer.parseInt(product.get(1).getNum2())) {
                        tv_one_price.setText(product.get(1).getUnitPrice3());
                        tv_order_number.setText("数量: " + num2 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice3()) * Float.parseFloat(num2)));
                    } else if (Integer.parseInt(product.get(1).getNum2()) <= Integer.parseInt(num2) && Integer.parseInt(num2) < Integer.parseInt(product.get(1).getNum3())) {
                        tv_one_price.setText(product.get(1).getUnitPrice2());
                        tv_order_number.setText("数量: " + num2 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice2()) * Float.parseFloat(num2)));
                    } else if (Integer.parseInt(num2) >= Integer.parseInt(product.get(1).getNum3())) {
                        tv_one_price.setText(product.get(1).getUnitPrice2());
                        tv_order_number.setText("数量: " + num2 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice1()) * Float.parseFloat(num2)));
                    }
                    tv_amount_payable.setText("应付金额:" + Float.parseFloat(product.get(1).getUnitPrice1()) * Float.parseFloat(num2));
                } else {
                    tv_name_order.setText(title);
                    tv_name_order1.setText(title);
                    if (Integer.parseInt(product.get(0).getNum1()) <= Integer.parseInt(num1) && Integer.parseInt(num1) < Integer.parseInt(product.get(0).getNum2())) {
                        tv_one_price.setText(product.get(0).getUnitPrice3());
                        tv_order_number.setText("数量: " + num1 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice3()) * Float.parseFloat(num1)));
                    } else if (Integer.parseInt(product.get(0).getNum2()) <= Integer.parseInt(num1) && Integer.parseInt(num1) < Integer.parseInt(product.get(0).getNum3())) {
                        tv_one_price.setText(product.get(0).getUnitPrice2());
                        tv_order_number.setText("数量: " + num1 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice2()) * Float.parseFloat(num1)));
                    } else if (Integer.parseInt(num1) >= Integer.parseInt(product.get(0).getNum3())) {
                        tv_one_price.setText(product.get(0).getUnitPrice2());
                        tv_order_number.setText("数量: " + num1 + "箱");
                        tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num1)));
                    }

                    if (Integer.parseInt(product.get(1).getNum1()) <= Integer.parseInt(num2) && Integer.parseInt(num2) < Integer.parseInt(product.get(1).getNum2())) {
                        tv_second_price.setText(product.get(1).getUnitPrice3());
                        tv_order_number1.setText("数量: " + num2 + "箱");
                        tv_second_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice3()) * Float.parseFloat(num2)));
                    } else if (Integer.parseInt(product.get(1).getNum2()) <= Integer.parseInt(num2) && Integer.parseInt(num2) < Integer.parseInt(product.get(1).getNum3())) {
                        tv_second_price.setText(product.get(1).getUnitPrice2());
                        tv_order_number1.setText("数量: " + num2 + "箱");
                        tv_second_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice2()) * Float.parseFloat(num2)));
                    } else if (Integer.parseInt(num2) >= Integer.parseInt(product.get(1).getNum3())) {
                        tv_second_price.setText(product.get(1).getUnitPrice2());
                        tv_order_number1.setText("数量: " + num2 + "箱");
                        tv_second_count.setText("合计:" + (Float.parseFloat(product.get(1).getUnitPrice1()) * Float.parseFloat(num2)));
                    }

                    tv_amount_payable.setText("应付金额:" + (Float.parseFloat(product.get(1).getUnitPrice1()) * Float.parseFloat(num2) + Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num1)));
                }

                jsonObject = new JSONObject();
                jsonArray = new JSONArray();

                jsonObject1 = new JSONObject();
                jsonObject1.put("goodsTypesId", product.get(0).getGoodsTypesId());
                jsonObject1.put("goodsNumber", num1);
                jsonArray.add(jsonObject1);

                jsonObject1.put("goodsTypesId", product.get(0).getGoodsTypesId());
                jsonObject1.put("goodsNumber", num2);
                jsonArray.add(jsonObject1);

                jsonObject.put("goodsId", goodsId);
                jsonObject.put("goodsType", jsonArray);

                jsonArray1 = new JSONArray();
                jsonArray1.add(jsonObject);
                generJson = jsonArray1.toString();
                break;

            case "1":
                view1.setVisibility(View.GONE);
                rl_item_second.setVisibility(View.GONE);
                tv_name_order.setText(title);
                if (Integer.parseInt(product.get(0).getNum1()) <= Integer.parseInt(num) && Integer.parseInt(num) < Integer.parseInt(product.get(0).getNum2())) {
                    tv_one_price.setText(product.get(0).getUnitPrice3());
                    tv_order_number.setText("数量: " + num + "箱");
                    tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice3()) * Float.parseFloat(num)));

                    tv_amount_payable.setText("应付金额:" + Float.parseFloat(product.get(0).getUnitPrice3()) * Float.parseFloat(num));
                } else if (Integer.parseInt(product.get(0).getNum2()) <= Integer.parseInt(num) && Integer.parseInt(num) < Integer.parseInt(product.get(0).getNum3())) {
                    tv_one_price.setText(product.get(0).getUnitPrice2());
                    tv_order_number.setText("数量: " + num + "箱");
                    tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice2()) * Float.parseFloat(num)));

                    tv_amount_payable.setText("应付金额:" + Float.parseFloat(product.get(0).getUnitPrice2()) * Float.parseFloat(num));
                } else if (Integer.parseInt(num) >= Integer.parseInt(product.get(0).getNum3())) {
                    tv_one_price.setText(product.get(0).getUnitPrice1());
                    tv_order_number.setText("数量: " + num + "箱");
                    tv_one_count.setText("合计:" + (Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num)));

                    tv_amount_payable.setText("应付金额:" + Float.parseFloat(product.get(0).getUnitPrice1()) * Float.parseFloat(num));
                }

                jsonObject = new JSONObject();
                jsonArray = new JSONArray();

                jsonObject1 = new JSONObject();
                jsonObject1.put("goodsTypesId", product.get(0).getGoodsTypesId());
                jsonObject1.put("goodsNumber", num);
                jsonArray.add(jsonObject1);

                jsonObject.put("goodsId", goodsId);
                jsonObject.put("goodsType", jsonArray);
                jsonArray1 = new JSONArray();
                jsonArray1.add(jsonObject);
                generJson = jsonArray1.toString();

                break;

            case "0":

                view1.setVisibility(View.GONE);
                rl_item_second.setVisibility(View.GONE);

                tv_name_order.setText(title);
                tv_one_price.setText(product.get(0).getUnitPrice1());
                tv_order_number.setText("数量: " + num + "箱");
                tv_one_count.setText("合计:" + Float.parseFloat(num) * Float.parseFloat(product.get(0).getUnitPrice1()));
                tv_amount_payable.setText("应付金额:" + Float.parseFloat(num) * Float.parseFloat(product.get(0).getUnitPrice1()));

                jsonObject = new JSONObject();
                jsonArray = new JSONArray();

                jsonObject1 = new JSONObject();
                jsonObject1.put("goodsTypesId", product.get(0).getGoodsTypesId());
                jsonObject1.put("goodsNumber", num);
                jsonArray.add(jsonObject1);

                jsonObject.put("goodsId", goodsId);
                jsonObject.put("goodsType", jsonArray);
                jsonArray1 = new JSONArray();
                jsonArray1.add(jsonObject);
                generJson = jsonArray1.toString();
                break;
        }
    }

    private void initView() {
        //返回按钮
        rl_confirm_back = (RelativeLayout) findViewById(R.id.rl_confirm_back);

        //商品的订单的详情内容
        image = (ImageView) findViewById(R.id.image);
        tv_name_order = (TextView) findViewById(R.id.tv_name_order);
        tv_order_stande = (TextView) findViewById(R.id.tv_order_stande);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_one_price = (TextView) findViewById(R.id.tv_one_price);
        tv_one_count = (TextView) findViewById(R.id.tv_one_count);

        view1 = (View) findViewById(R.id.view1);
        rl_item_second = (RelativeLayout) findViewById(R.id.rl_item_second);
        image01 = (ImageView) findViewById(R.id.image01);
        tv_name_order1 = (TextView) findViewById(R.id.tv_name_order1);
        tv_order_stande1 = (TextView) findViewById(R.id.tv_order_stande1);
        tv_order_number1 = (TextView) findViewById(R.id.tv_order_number1);
        tv_second_price = (TextView) findViewById(R.id.tv_second_price);
        tv_second_count = (TextView) findViewById(R.id.tv_second_count);

        rl_union_pay = (RelativeLayout) findViewById(R.id.rl_union_pay);
        rl_off_line_pay = (RelativeLayout) findViewById(R.id.rl_off_line_pay);
        rl_wechert_pay = (RelativeLayout) findViewById(R.id.rl_wechert_pay);
        rl_zhifubao_pay = (RelativeLayout) findViewById(R.id.rl_zhifubao_pay);
        //支付方式
        iv_off_line_pay = (ImageView) findViewById(R.id.iv_off_line_pay);
        iv_wechart_pay = (ImageView) findViewById(R.id.iv_wechart_pay);
        iv_zhifubao_pay = (ImageView) findViewById(R.id.iv_zhifubao_pay);
        iv_union_pay = (ImageView) findViewById(R.id.iv_union_pay);

        //付款金额和确认支付
        tv_confirm_pay = (TextView) findViewById(R.id.tv_confirm_pay);
        tv_amount_payable = (TextView) findViewById(R.id.tv_amount_payable);

        rl_confirm_back.setOnClickListener(this);
        iv_off_line_pay.setOnClickListener(this);
        iv_wechart_pay.setOnClickListener(this);
        iv_zhifubao_pay.setOnClickListener(this);
        iv_union_pay.setOnClickListener(this);
        tv_confirm_pay.setOnClickListener(this);

        rl_union_pay.setOnClickListener(this);
        rl_off_line_pay.setOnClickListener(this);
        rl_wechert_pay.setOnClickListener(this);
        rl_zhifubao_pay.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_confirm_back://返回上一个页面
                finish();
                break;
            case R.id.rl_off_line_pay://线下支付
                iv_off_line_pay.setImageResource(R.drawable.select);
                iv_wechart_pay.setImageResource(R.drawable.unseclect);
                iv_zhifubao_pay.setImageResource(R.drawable.unseclect);
                iv_union_pay.setImageResource(R.drawable.unseclect);
                flag = 0;
                break;
            case R.id.rl_wechert_pay://微信支付
                iv_wechart_pay.setImageResource(R.drawable.select);

                iv_off_line_pay.setImageResource(R.drawable.unseclect);
                iv_zhifubao_pay.setImageResource(R.drawable.unseclect);
                iv_union_pay.setImageResource(R.drawable.unseclect);
                flag = 1;
                break;
            case R.id.rl_zhifubao_pay://支付宝支付
                iv_zhifubao_pay.setImageResource(R.drawable.select);

                iv_wechart_pay.setImageResource(R.drawable.unseclect);
                iv_off_line_pay.setImageResource(R.drawable.unseclect);
                iv_union_pay.setImageResource(R.drawable.unseclect);
                flag = 2;
                break;
            case R.id.rl_union_pay://银联支付
                iv_union_pay.setImageResource(R.drawable.select);

                iv_wechart_pay.setImageResource(R.drawable.unseclect);
                iv_zhifubao_pay.setImageResource(R.drawable.unseclect);
                iv_off_line_pay.setImageResource(R.drawable.unseclect);
                flag = 3;
                break;
            case R.id.tv_confirm_pay://确认支付
                intoConfirmPay(flag);
                break;
        }

    }

    /**
     * 确认支付
     *
     * @param flag
     */
    private void intoConfirmPay(int flag) {

        if (flag == 0) {//线下支付
            generateOrder();
            // Intent intent=new Intent(ConfirmOrderActivity.this,OffLineActivity.class);
            // startActivity(intent);
            //联网生成订单

        } else if (flag == 1) {//微信支付

        } else if (flag == 2) {//支付宝支付

        } else if (flag == 3) {//银联支付

        }
    }

    /**
     * 生成订单
     */
    private void generateOrder() {
        System.out.print("生成订单成功++++++++" + generJson);
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addQueryStringParameter("MerchantId", "1");
        requestParams.addQueryStringParameter("adressId", "1");
        requestParams.addQueryStringParameter("consignee", name);
        requestParams.addQueryStringParameter("tel", phone);
        requestParams.addQueryStringParameter("address", address);
        requestParams.addQueryStringParameter("fromPlat", "0");
        requestParams.addQueryStringParameter("json", generJson);

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.GENERATE_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print("生成订单成功++++++++" + json);
                JSONObject jsonObject = JSON.parseObject(json);
                String state = (String) jsonObject.get("state");

                if ("0".equals(state)) {
                    Toast.makeText(ConfirmOrderActivity.this, "订单生成成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmOrderActivity.this, OffLineActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {

                Toast.makeText(ConfirmOrderActivity.this, "订单生成失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
