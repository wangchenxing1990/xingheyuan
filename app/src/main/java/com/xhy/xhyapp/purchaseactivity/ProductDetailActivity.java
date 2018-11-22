
package com.xhy.xhyapp.purchaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.ApplyActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyGridViewLeft;
import com.xhy.xhyapp.adapter.MyGridViewRight;
import com.xhy.xhyapp.bean.AddressListBean;
import com.xhy.xhyapp.bean.ProductDatailBean1;
import com.xhy.xhyapp.bean.ProductDetailBean123;
import com.xhy.xhyapp.bean.ProductDtailBean;
import com.xhy.xhyapp.content.ContentUrl;
import com.xhy.xhyapp.myactivity.AddAddressActivity;
import com.xhy.xhyapp.myactivity.ConfirmOrderActivity;
import com.xhy.xhyapp.myactivity.ShippingAddressManagementActivity;
import com.xhy.xhyapp.view.BuyPopubWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ProductDetailActivity extends Activity implements View.OnClickListener {

    private TextView tv_pro_weight, tv_direct_pro, tv_add_collect, tv_contact_us, tv_pro_name1, tv_pro_price1, tv_pro_name2, tv_pro_numb, tv_pro_price2, tv_product_brand, tv_detail_address;
    private TextView tv_detail_level, tv_brand_introduce, tv_standard;
    private TextView tv_direct_price_high, tv_direct_price_middle, tv_direct_price_low, tv_direct_weght_low, tv_direct_weght_high, tv_direct_weght_middle;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager direct_propure_pager;
    private ImageView iv_direct_back;
    private String imageviewTitle;

    private GridView grid_view_left, grid_view_right;

    private MyPagerAdapter adapter;
    private BuyPopubWindow buyPopubWindow;
    private String[] headerUrls;

    private RelativeLayout rl_icon_text_detail, rl_detail_name_one;
    private String goodsId, merchantId, goodsStyleType;
    private List<ProductDetailBean123> listTypeInfo = new ArrayList<>();
    private ArrayList<ProductDtailBean> listDataInfo = new ArrayList<>();
    private String[] dataUrls;
    //轮播图数据
    private List<Integer> images = new ArrayList();
    private ProductDtailBean productDtailBean;
    private ProductDtailBean productDtailBean1;
    private ProductDtailBean productDtailBean2;
    private ProductDatailBean1 productDtailBean3;
    private ProductDetailBean123 productDtailBean123;
    private LinearLayout ll_detail_name_two, ll_detail_price_two, ll_detail_price_one;
    private String name;
    private String phone;
    private String address;
    private  Boolean isFlag;
    private TextView txt_window_name;
    private TextView txt_window_phone;
    private TextView txt_shouhuo_dizhi;

    private List<AddressListBean> listAddress = new ArrayList();
    private AddressListBean addressListBean;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_scroll_pager:
                    int nextItem = (direct_propure_pager.getCurrentItem() + 1) % headerUrls.length;
                    direct_propure_pager.setCurrentItem(nextItem);
                    handler.sendEmptyMessageDelayed(msg_scroll_pager, 3000);
                    break;
            }
        }
    };

    public boolean onTouchEvent(MotionEvent event) {

        if (buyPopubWindow != null && buyPopubWindow.isShowing()) {
            buyPopubWindow.dismiss();
            buyPopubWindow = null;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity12);

        Intent intent = getIntent();
        merchantId = intent.getStringExtra("merchantId");
        imageviewTitle = intent.getStringExtra("imageview");
        goodsId = intent.getStringExtra("goodsId");
        goodsStyleType = intent.getStringExtra("goodsStyleType");

        requestAddressData();
        initView();
        requestData();
        initData();

    }


    private void initView() {
        //初始化数据
        direct_propure_pager = (ViewPager) findViewById(R.id.direct_propure_pager1);

        rl_icon_text_detail = (RelativeLayout) findViewById(R.id.rl_icon_text_detail);

        rl_detail_name_one = (RelativeLayout) findViewById(R.id.rl_detail_name_one);

        ll_detail_name_two = (LinearLayout) findViewById(R.id.ll_detail_name_two);

        ll_detail_price_two = (LinearLayout) findViewById(R.id.ll_detail_price_two);

        ll_detail_price_one = (LinearLayout) findViewById(R.id.ll_detail_price_one);


        tv_pro_name1 = (TextView) findViewById(R.id.tv_pro_name1);
        tv_pro_price1 = (TextView) findViewById(R.id.tv_pro_price1);
        tv_pro_name2 = (TextView) findViewById(R.id.tv_pro_name2);
        tv_pro_numb = (TextView) findViewById(R.id.tv_pro_numb);

        tv_pro_weight = (TextView) findViewById(R.id.tv_pro_weight);

        tv_product_brand = (TextView) findViewById(R.id.tv_product_brand);
        tv_detail_address = (TextView) findViewById(R.id.tv_detail_address);
        tv_detail_level = (TextView) findViewById(R.id.tv_detail_level);
        tv_brand_introduce = (TextView) findViewById(R.id.tv_brand_introduce);
        tv_pro_price2 = (TextView) findViewById(R.id.tv_pro_price2);

        tv_direct_price_high = (TextView) findViewById(R.id.tv_direct_price_high);
        tv_direct_price_middle = (TextView) findViewById(R.id.tv_direct_price_middle);
        tv_direct_price_low = (TextView) findViewById(R.id.tv_direct_price_low);

        tv_direct_weght_high = (TextView) findViewById(R.id.tv_direct_weght_high);
        tv_direct_weght_middle = (TextView) findViewById(R.id.tv_direct_weght_middle);
        tv_direct_weght_low = (TextView) findViewById(R.id.tv_direct_weght_low);

        tv_standard = (TextView) findViewById(R.id.tv_standard);

        grid_view_left = (GridView) findViewById(R.id.grid_view_left);

        grid_view_right = (GridView) findViewById(R.id.grid_view_right);


        //最下面的三个点击按钮
        tv_direct_pro = (TextView) findViewById(R.id.tv_direct_pro);
        tv_add_collect = (TextView) findViewById(R.id.tv_add_collect);
        tv_contact_us = (TextView) findViewById(R.id.tv_contact_us);
        //点击返回上一页图片
        iv_direct_back = (ImageView) findViewById(R.id.iv_direct_back);


        switch (goodsStyleType) {
            case "0":
                rl_detail_name_one.setVisibility(View.VISIBLE);
                ll_detail_name_two.setVisibility(View.GONE);
                ll_detail_price_one.setVisibility(View.GONE);
                ll_detail_price_two.setVisibility(View.GONE);
                break;
            case "1":
                ll_detail_name_two.setVisibility(View.VISIBLE);
                ll_detail_price_two.setVisibility(View.VISIBLE);

                rl_detail_name_one.setVisibility(View.GONE);
                ll_detail_price_one.setVisibility(View.GONE);
                //tv_pro_name2.setText(productDtailBean3.getBrand());
                break;
            case "2":
                ll_detail_name_two.setVisibility(View.VISIBLE);
                ll_detail_price_one.setVisibility(View.VISIBLE);

                rl_detail_name_one.setVisibility(View.GONE);
                ll_detail_price_two.setVisibility(View.GONE);
                break;
        }


        //setParamer();
        //三个按钮的点击监听事件
        // tv_direct_pro.setOnClickListener(this);
        tv_add_collect.setOnClickListener(this);
        tv_contact_us.setOnClickListener(this);

        rl_icon_text_detail.setOnClickListener(this);

        //点击返回上一个界面的图片的点击监听
        iv_direct_back.setOnClickListener(this);

        tv_direct_pro.setOnClickListener(new View.OnClickListener() {
            private ImageView image_cancel;
            private Button image_surebuy;
            private Button image_apply;
            private ImageView image_jia;
            private ImageView image_jian;
            private ImageView imageview_sure;
            private ImageView imageView_jian03;
            private ImageView imageView_jia_l03;
            private EditText edittext_l03;
            private EditText edittext4;
            private EditText edittext_shuliang;

            private ImageView imageView_jian;
            private ImageView imageView_jian02;
            private ImageView imageView_jia02;

            private RelativeLayout relative_dizhi;

            private TextView txt_name_sure;
            private RelativeLayout rl_zhiyou_l01;
            private RelativeLayout rl_l01andl02;

            @Override
            public void onClick(View view) {

                buyPopubWindow = new BuyPopubWindow(ProductDetailActivity.this, itemsOnClick, R.layout.activity_buy_popupwindow);
                //显示窗口  设置layout在PopupWindow中显示的位置
                buyPopubWindow.showAtLocation(ProductDetailActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view1 = buyPopubWindow.getView();

                imageview_sure = (ImageView) view1.findViewById(R.id.imageview_sure);
                txt_name_sure = (TextView) view1.findViewById(R.id.txt_name_sure);

                imageView_jian03 = (ImageView) view1.findViewById(R.id.imageView_jian03);
                imageView_jia_l03 = (ImageView) view1.findViewById(R.id.imageView_jia_l03);
                edittext_l03 = (EditText) view1.findViewById(R.id.edittext_l03);
                edittext_l03.setSelection(edittext_l03.getText().length());
                image_jian = ((ImageView) view1.findViewById(R.id.imageView_jian));
                image_jia = ((ImageView) view1.findViewById(R.id.imageView_jia));
                edittext4 = (EditText) view1.findViewById(R.id.edittext4);

                imageView_jian02 = (ImageView) view1.findViewById(R.id.imageView_jian02);
                edittext_shuliang = (EditText) view1.findViewById(R.id.edittext_shuliang);
                imageView_jia02 = (ImageView) view1.findViewById(R.id.imageView_jia02);

                image_apply = ((Button) view1.findViewById(R.id.image_apply));
                image_surebuy = ((Button) view1.findViewById(R.id.image_surebuy));
                image_cancel = ((ImageView) view1.findViewById(R.id.image_cancel));

                rl_zhiyou_l01 = (RelativeLayout) view1.findViewById(R.id.rl_zhiyou_l01);
                rl_l01andl02 = (RelativeLayout) view1.findViewById(R.id.rl_l01andl02);

                relative_dizhi = (RelativeLayout) view1.findViewById(R.id.relative_dizhi);
                txt_window_name = (TextView) view1.findViewById(R.id.txt_window_name);
                txt_window_phone = (TextView) view1.findViewById(R.id.txt_window_phone);
                txt_shouhuo_dizhi = (TextView) view1.findViewById(R.id.txt_shouhuo_dizhi);

                switch (goodsStyleType) {

                    case "2":
                        rl_l01andl02.setVisibility(View.VISIBLE);
                        rl_zhiyou_l01.setVisibility(View.GONE);

                        break;
                    default:
                        rl_l01andl02.setVisibility(View.GONE);
                        rl_zhiyou_l01.setVisibility(View.VISIBLE);
                        break;
                }
                txt_name_sure.setText(productDtailBean3.getTitle());
                BitmapUtils bitmapUtils = new BitmapUtils(ProductDetailActivity.this);
                bitmapUtils.display(imageview_sure, imageviewTitle);



                for (int i = 0; i < listAddress.size(); i++) {
                    if (Boolean.parseBoolean(listAddress.get(i).getIsDefault())) {
                        txt_window_name.setText(listAddress.get(i).getConsignee());
                        txt_window_phone.setText(listAddress.get(i).getTel());
                        txt_shouhuo_dizhi.setText(listAddress.get(i).getAddress());
                        isFlag = true;
                        break;
                    } else {
                        isFlag = false;
                    }
                }


                image_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buyPopubWindow.dismiss();
                    }
                });

                relative_dizhi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isFlag) {
                            Intent intent = new Intent(ProductDetailActivity.this, ShippingAddressManagementActivity.class);
                            startActivity(intent);

                        } else {
                            Intent intent = new Intent(ProductDetailActivity.this, AddAddressActivity.class);
                            startActivityForResult(intent, 00);
                        }
                    }
                });

                imageView_jian03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number_three = edittext_l03.getText().toString().trim();
                        int number_three_int = Integer.parseInt(number_three);
                        if (number_three_int < 0) {
                            return;
                        } else {
                            number_three_int--;
                            edittext_l03.setText(number_three_int + "");
                            edittext_l03.setSelection(edittext_l03.getText().length());
                        }
                    }
                });

                imageView_jia_l03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number_three = edittext_l03.getText().toString().trim();
                        int number_three_int = Integer.parseInt(number_three);
                        number_three_int++;
                        edittext_l03.setText(number_three_int + "");
                        edittext_l03.setSelection(edittext_l03.getText().length());
                    }
                });

                imageView_jian02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number_two = edittext_shuliang.getText().toString().trim();
                        int number_two_int = Integer.parseInt(number_two);
                        if (number_two_int < 0) {
                            return;
                        } else {
                            number_two_int--;
                            edittext_shuliang.setText(number_two_int + "");
                            edittext_shuliang.setSelection(edittext_shuliang.getText().length());
                        }
                    }
                });

                imageView_jia02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number_two = edittext_shuliang.getText().toString().trim();
                        int number_two_int = Integer.parseInt(number_two);

                        number_two_int++;
                        edittext_shuliang.setText(number_two_int + "");
                        edittext_shuliang.setSelection(edittext_shuliang.getText().length());
                    }
                });

                image_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductDetailActivity.this, ApplyActivity.class);
                        startActivity(intent);
                    }
                });

                image_jia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ProductDetailActivity.this, "加号", Toast.LENGTH_LONG).show();
                        String number_one_jia = edittext4.getText().toString().trim();
                        int number_int_one = Integer.parseInt(number_one_jia);
                        if (number_int_one < 0) {

                            return;
                        } else {
                            number_int_one++;
                            edittext4.setText(number_int_one + "");
                            edittext4.setSelection(edittext4.getText().length());
                        }
                    }
                });

                image_jian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ProductDetailActivity.this, "减号", Toast.LENGTH_LONG).show();
                        String number_one = edittext4.getText().toString().trim();
                        int number_int_one = Integer.parseInt(number_one);
                        if (number_int_one < 0) {

                            return;
                        } else {
                            number_int_one--;
                            edittext4.setText(number_int_one + "");
                            edittext4.setSelection(edittext4.getText().length());
                        }
                    }
                });


                image_surebuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle mBundle = new Bundle();
                        Intent intent = new Intent(ProductDetailActivity.this, ConfirmOrderActivity.class);

                        mBundle.putSerializable("product", (Serializable) listTypeInfo);
                        intent.putExtras(mBundle);
                        intent.putExtra("imageview", imageviewTitle);
                        intent.putExtra("title", productDtailBean3.getTitle());
                        intent.putExtra("goodsId", productDtailBean3.getGoodsId());

                        String numberone = edittext_l03.getText().toString().trim();
                        String numbertwo = edittext4.getText().toString().trim();
                        String numberthree = edittext_shuliang.getText().toString().trim();

                        intent.putExtra("goodsStyleType", goodsStyleType);

                        System.out.print("jfoijfojo+++++++++++++++++++++++++" + numberone + numbertwo + numberthree);

                        intent.putExtra("num", numberone);
                        intent.putExtra("num1", numbertwo);
                        intent.putExtra("num2", numberthree);

                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("address", address);

                        startActivity(intent);
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2 && requestCode == 00) {
            name = data.getStringExtra("name");
            phone = data.getStringExtra("phone");
            address = data.getStringExtra("address");

            txt_window_name.setText(name);
            txt_window_phone.setText(phone);
            txt_shouhuo_dizhi.setText(address);
        }
    }

    //为弹出窗口实现监听类
    View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            buyPopubWindow.dismiss();
        }
    };

    /**
     * 初始化轮播图数据
     */
    private void initData() {

        images.add(R.drawable.pitaya0);
        images.add(R.drawable.pitaya1);
        images.add(R.drawable.pitaya2);

    }

    private static final int msg_scroll_pager = 100;

    /**
     * 实现自动轮播
     *
     * @param headerUrlss
     */
    private void stratSroll(String[] headerUrlss) {
        if (adapter == null) {
            //viewpager设置adpater
            adapter = new MyPagerAdapter(headerUrlss);
            direct_propure_pager.setAdapter(adapter);
        } else {

            adapter.notifyDataSetChanged();

        }
        //开始轮播
        handler.sendEmptyMessageDelayed(msg_scroll_pager, 2000);
    }

    /**
     * @param v
     */
    @Override

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_add_collect:
                // Toast.makeText(getApplication(), "已添加至收藏 ", Toast.LENGTH_SHORT).show();
                //添加收藏
                addConllection123(goodsId);
                break;
            case R.id.tv_contact_us:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "10086"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.rl_icon_text_detail:
                Bundle mbundle = new Bundle();
                mbundle.putSerializable("user", (Serializable) listDataInfo);
                mbundle.putSerializable("dataUrls", dataUrls);
                Intent intent1 = new Intent(ProductDetailActivity.this, IconTextDetailActivity.class);
                intent1.putExtras(mbundle);

                startActivity(intent1);
                break;
            case R.id.iv_direct_back:
                finish();
                break;
        }
    }

    /**
     * 获取地址数据
     */
    private void requestAddressData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.GET_MERCHANT_ADDRESS_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print("请求地址数据成功" + json);
                parseJsonAddress(json);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });

    }

    /**
     * 解析地址数据
     *
     * @param json
     */
    private void parseJsonAddress(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String state = (String) jsonObject.get("state");
        if ("0".equals(state)) {
            Toast.makeText(ProductDetailActivity.this, "解析数据", Toast.LENGTH_SHORT).show();
        }
        JSONArray jsonArray = jsonObject.getJSONArray("addressList");
        listAddress.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            addressListBean = new AddressListBean();
            JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));
            addressListBean.setAddressId((String) jsonObject1.get("addressId"));
            addressListBean.setTitle((String) jsonObject1.get("title"));
            addressListBean.setConsignee((String) jsonObject1.get("consignee"));
            addressListBean.setTel((String) jsonObject1.get("tel"));
            addressListBean.setAddress((String) jsonObject1.get("address"));
            addressListBean.setIsDefault((String) jsonObject1.get("isDefault"));

            listAddress.add(addressListBean);
        }

    }

    /**
     * 添加收藏
     *
     * @param
     * @param goodsId
     */
    private void addConllection123(String goodsId) {

        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("MerchantId", "1");
        requestParams.addQueryStringParameter("GoodsId", goodsId);

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.ADD_COLLECTION_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print(json);

                JSONObject jsonObject = JSON.parseObject(json);
                String statee = (String) jsonObject.get("state");
                String msg = (String) jsonObject.get("msg");
                System.out.print("实际的巅峰是" + statee);

                if (statee == "0") {
                    Toast.makeText(ProductDetailActivity.this, "已添加收藏", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ProductDetailActivity.this, "已添加收藏" + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                Toast.makeText(ProductDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();

            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        private final String[] headerUrlsss;

        public MyPagerAdapter(String[] headerUrlsss) {
            this.headerUrlsss = headerUrlsss;
        }

        @Override
        public int getCount() {
            return headerUrlsss.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(getApplication(), R.layout.roll_view_pager, null);
            ImageView image_pager = (ImageView) view.findViewById(R.id.image_propure_pager);

            BitmapUtils bitmapUtil = new BitmapUtils(ProductDetailActivity.this);
            bitmapUtil.display(image_pager, headerUrlsss[position]);

            container.addView(image_pager);

            return view;
        }
    }

    private void requestData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("GoodsId", goodsId);
        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.GET_GOODS_DETAIL_URL, requestParams, new RequestCallBack<String>() {


            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.print("请求产品详情数据成功+++++++++" + json);

                JSONObject jsonObject = JSON.parseObject(json);

                String datailImageList = (String) jsonObject.get("goodsDetailImgList");
                dataUrls = datailImageList.split(",");

                String headerImageList = (String) jsonObject.get("goodsHeaderImgList");
                headerUrls = headerImageList.split(",");

                stratSroll(headerUrls);

                JSONArray jsonArray = jsonObject.getJSONArray("typeInfo");

                JSONArray jsonArray1 = jsonObject.getJSONArray("dataInfo");

                productDtailBean3 = new ProductDatailBean1();

                productDtailBean3.setPlace(jsonObject.getString("place"));
                productDtailBean3.setTitle(jsonObject.getString("title"));
                productDtailBean3.setBrand(jsonObject.getString("brand"));
                productDtailBean3.setBrandIntroduction(jsonObject.getString("brandIntroduction"));
                productDtailBean3.setGrade(jsonObject.getString("grade"));
                productDtailBean3.setLabel(jsonObject.getString("label"));
                productDtailBean3.setGoodsId(jsonObject.getString("goodsId"));

                System.out.print("wangyukui1lkjflij" + jsonArray.size());
                listTypeInfo.clear();
                for (int i = 0; i < jsonArray.size(); i++) {
                    productDtailBean123 = new ProductDetailBean123();
                    JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));

                    productDtailBean123.setNum1(jsonObject1.getString("num1"));
                    productDtailBean123.setNum2(jsonObject1.getString("num2"));
                    productDtailBean123.setNum3(jsonObject1.getString("num3"));

                    productDtailBean123.setUnitPrice1(jsonObject1.getString("unitPrice1"));
                    productDtailBean123.setUnitPrice2(jsonObject1.getString("unitPrice2"));
                    productDtailBean123.setUnitPrice3(jsonObject1.getString("unitPrice3"));

                    productDtailBean123.setGoodsTypeName(jsonObject1.getString("goodsTypeName"));
                    productDtailBean123.setGoodsTypesId(jsonObject1.getString("goodsTypesId"));

                    listTypeInfo.add(productDtailBean123);
                }

                System.out.print("ldjgfdewfoidjspkfjfjoiuj89reoijfjsoifjjego" + listTypeInfo.size());
                setDataProduct(productDtailBean3, listTypeInfo);

                listDataInfo.clear();
                for (int i = 0; i < jsonArray1.size(); i++) {
                    productDtailBean1 = new ProductDtailBean();
                    JSONObject jsonObject2 = JSON.parseObject(String.valueOf(jsonArray1.get(i)));
                    productDtailBean1.setName(jsonObject2.getString("name"));
                    productDtailBean1.setValue(jsonObject2.getString("value"));

                    listDataInfo.add(productDtailBean1);
                }
                System.out.print("请求产品详情数据成功+++++++++" + listDataInfo.get(0).getName());
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    public void setDataProduct(ProductDatailBean1 dataProduct, List<ProductDetailBean123> listTypeInfo1) {

        tv_pro_name2.setText(dataProduct.getTitle());
        tv_pro_price2.setText(listTypeInfo1.get(0).getUnitPrice1());
        tv_pro_weight.setText(listTypeInfo1.get(0).getStock());
        tv_product_brand.setText(dataProduct.getBrand());
        tv_detail_address.setText(dataProduct.getPlace());
        tv_detail_level.setText(dataProduct.getGrade());

        tv_pro_price1.setText(dataProduct.getLabel());

        tv_brand_introduce.setText("品牌介绍: " + dataProduct.getBrandIntroduction());

        tv_direct_weght_high.setText(listTypeInfo1.get(0).getNum3() + "件以上");
        tv_direct_price_low.setText(listTypeInfo1.get(0).getUnitPrice3());

        tv_direct_weght_middle.setText(listTypeInfo1.get(0).getNum2() + "-" + listTypeInfo1.get(0).getNum3() + "件");
        tv_direct_price_middle.setText(listTypeInfo1.get(0).getUnitPrice2());

        tv_direct_weght_low.setText(listTypeInfo1.get(0).getNum1() + "件起批");
        tv_direct_price_high.setText(listTypeInfo1.get(0).getUnitPrice1());

        tv_pro_name1.setText(dataProduct.getTitle());

        tv_standard.setText(listTypeInfo1.get(0).getGoodsTypeName());

        if (listTypeInfo1.size() == 1) {

            MyGridViewLeft adapterLeft = new MyGridViewLeft(ProductDetailActivity.this, listTypeInfo1);
            grid_view_left.setAdapter(adapterLeft);

        } else {

            MyGridViewLeft adapterLeft = new MyGridViewLeft(ProductDetailActivity.this, listTypeInfo1);
            grid_view_left.setAdapter(adapterLeft);

            MyGridViewRight adapterRight = new MyGridViewRight(ProductDetailActivity.this, listTypeInfo1);
            grid_view_right.setAdapter(adapterRight);

        }
    }
}
