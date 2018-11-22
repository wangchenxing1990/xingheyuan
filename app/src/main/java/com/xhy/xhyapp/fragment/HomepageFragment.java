package com.xhy.xhyapp.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.HomeSearchActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.HomePageAdapter;
import com.xhy.xhyapp.bean.HomePageViewPagerBean;
import com.xhy.xhyapp.bean.HomepageBean;
import com.xhy.xhyapp.homepageactivity.CollectActivity;
import com.xhy.xhyapp.myactivity.MyPurchaseActivity;
import com.xhy.xhyapp.purchaseactivity.ProductDetailActivity;
import com.xhy.xhyapp.storeactivity.StoreActivity;
import com.zxing.activity.CaptureActivity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HomepageFragment extends Fragment {

    private View inflate;
    private View img_saoyisao;
    private ListView listview_homepage;
    private ImageView[] tips;//提示性点点数组
    private int[] images;//图片ID数组
    private int currentPage = 0;//当前展示的页码
    private LinearLayout tipsBox;
    private ViewPager viewPager;
    private TextView edit_home;
    private LinearLayout ll_collect;
    private List<Integer> imageviews = new ArrayList<>();
    private LinearLayout ll_shoporder;
    private LinearLayout ll_order;
    List<HomePageViewPagerBean> home_viewpager;
    List<ImageView> list = new ArrayList<>();
    List<HomepageBean> homepageBeen = new ArrayList<>();
    HomepageBean homeBean;
    ImageView img01, img02, img03, img04;
    HomePageAdapter homepageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_homepage, container, false);
        return inflate;
    }

    @SuppressLint("ValidFragment")
    public HomepageFragment(ImageView img01, ImageView img02, ImageView img03, ImageView img04) {
        this.img01 = img01;
        this.img02 = img02;
        this.img03 = img03;
        this.img04 = img04;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_home = ((TextView) inflate.findViewById(R.id.edit_homepage));
        edit_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
                edit_home.setInputType(InputType.TYPE_NULL);
                startActivity(intent);
            }
        });
        img_saoyisao = inflate.findViewById(R.id.img_saoyisao);
        listview_homepage = ((ListView) inflate.findViewById(R.id.listview_homepage));
        initData();
        //  requestData();

//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0:
//                        for (int i = 0; i < home_viewpager.size(); i++) {
//                            // context = Context.getMainLooper();
//                            ImageView myImageView = new ImageView(getContext());
//                            // bitmapUtils.display(myImageView, home_viewpager.get(i).getImage());
//                            Picasso.with(getActivity()).load(home_viewpager.get(i).getImage()).into(myImageView);
//                            list.add(myImageView);
//                        }
//                        viewPager.setAdapter(new A());
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
        //viewpager和4个功能模块
        View headView = View.inflate(getActivity(), R.layout.homelistview_head, null);
        ll_collect = ((LinearLayout) headView.findViewById(R.id.ll_collect));
        ll_shoporder = ((LinearLayout) headView.findViewById(R.id.ll_shoporder));
        ll_order = ((LinearLayout) headView.findViewById(R.id.ll_order));
        tipsBox = ((LinearLayout) headView.findViewById(R.id.head_ll));
        viewPager = ((ViewPager) headView.findViewById(R.id.viewpager_home));

        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoreActivity.class);
                startActivity(intent);
            }
        });

        ll_shoporder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), MyPurchaseActivity.class);
                intent1.putExtra("name", "2");
                startActivity(intent1);
            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        images = new int[]{R.drawable.mangguo_01_11, R.drawable.chelizi_19};
        listview_homepage.addHeaderView(headView);
        //初始化 提示点点
        tips = new ImageView[images.length];
        new Thread(new Runnable() {
            @Override
            public void run() {
                ViewUtils.inject(this, view);
                list.clear();
                System.out.println("-------list------"+list.size());
                HttpUtils httpUtils = new HttpUtils();

                httpUtils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/Other/GetAds?merchantId=1&Type=1", new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    JSONArray adsList = json.getJSONArray("adsList");
                                    home_viewpager = JSON.parseArray(adsList.toString(), HomePageViewPagerBean.class);
//                                    list.clear();
                                    if (home_viewpager == null) return;
                                    switch (home_viewpager.size()) {
                                        case 1:
                                            Picasso.with(getActivity()).load(home_viewpager.get(0).getImage()).into(img01);
                                            list.add(img01);
                                            break;
                                        case 2:
                                            Picasso.with(getActivity()).load(home_viewpager.get(0).getImage()).into(img01);
                                            Picasso.with(getActivity()).load(home_viewpager.get(1).getImage()).into(img02);
                                            list.add(img01);
                                            list.add(img02);
                                            break;
//                                        case 3:
//                                            Picasso.with(getActivity()).load(home_viewpager.get(0).getImage()).into(img01);
//                                            Picasso.with(getActivity()).load(home_viewpager.get(1).getImage()).into(img02);
//                                            Picasso.with(getActivity()).load(home_viewpager.get(2).getImage()).into(img03);
//                                            list.add(img01);
//                                            list.add(img02);
//                                            list.add(img03);
//                                            break;
//                                        case 4:
//                                            break;
                                        default:
                                            break;
                                    }
//                                    Message me = new Message();
//                                    me.what = 0;
//                                    handler.sendMessage(me);
                                    viewPager.setAdapter(new A());


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

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/Other/GetAds?merchantId=1&Type=0", new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(result);
                                    String state = json.getString("state");
                                    if ("0".equals(state)) {
                                        JSONArray adsList = json.getJSONArray("adsList");
                                        homepageBeen.clear();
                                        for (int i = 0; i < adsList.length(); i++) {
                                            JSONObject json01 = adsList.getJSONObject(i);
                                            String adsName = json01.getString("adsName");
                                            String adsTitle = json01.getString("adsTitle");
                                            String price = json01.getString("price");
                                            String goodsId = json01.getString("goodsId");
                                            String goodsStyleType = json01.getString("goodsStyleType");

                                            homeBean = new HomepageBean();
                                            homeBean.setJieshao(adsName);
                                            homeBean.setName(adsTitle);
                                            homeBean.setPrice(price);
                                            homeBean.setImage(json01.getString("adsImg"));
                                            homeBean.setGoodsId(goodsId);
                                            homeBean.setGoodsStyleType(goodsStyleType);
                                            homepageBeen.add(homeBean);
                                        }
                                        homepageAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(getContext(), "获取失败！", Toast.LENGTH_SHORT).show();
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
        homepageAdapter = new HomePageAdapter(getContext(), homepageBeen);
        listview_homepage.setAdapter(homepageAdapter);
        //listview 条目跳转
        listview_homepage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("MerchantId","1");
                intent.putExtra("goodsId", homepageBeen.get(i-1).getGoodsId());
                intent.putExtra("goodsStyleType",homepageBeen.get(i-1).getGoodsStyleType() );
                startActivity(intent);
            }
        });
        for (int i = 0; i < tips.length; i++) {
            ImageView img = new ImageView(getActivity()); //实例化一个点点
            img.setLayoutParams(new ViewGroup.LayoutParams(12, 12));
            tips[i] = img;
            if (i == 0) {
                img.setBackgroundResource(R.drawable.welcome_page_new_dot_selected);
            } else {
                img.setBackgroundResource(R.drawable.welcome_page_new_dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            tipsBox.addView(img, params); //把点点添加到容器中
        }

        //更改当前tip
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tips.length; i++) {
                    if(i == position){
                        tips[position].setBackgroundResource(R.drawable.welcome_page_new_dot_selected);
                    }else{
                        tips[i].setBackgroundResource(R.drawable.welcome_page_new_dot_normal);
                    }
                }
            }
        });

        img_saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 0x100);
            }
        });
    }

//    private void requestData() {
//        HttpUtils httpUtils = new HttpUtils();
//        RequestParams requestParams = new RequestParams();
//        requestParams.addQueryStringParameter("merchantId", "1");
//       // requestParams.addQueryStringParameter("GoodsId", );
//        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.GET_GOODS_DETAIL_URL, requestParams, new RequestCallBack<String>() {
//
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                System.out.print("请求产品详情数据成功+++++++++" + json);
//                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(json);
//                System.out.println("--------jsonObject-----" + jsonObject);
//                String datailImageList = (String) jsonObject.get("goodsDetailImgList");
//                dataUrls = datailImageList.split(",");
//                String headerImageList = (String) jsonObject.get("goodsHeaderImgList");
//                headerUrls = headerImageList.split(",");
//               // stratSroll(headerUrls);
//
//                com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("typeInfo");
//                com.alibaba.fastjson.JSONArray jsonArray1 = jsonObject.getJSONArray("dataInfo");
//                productDtailBean3 = new ProductDatailBean1();
//                productDtailBean3.setPlace(jsonObject.getString("place"));
//                productDtailBean3.setTitle(jsonObject.getString("title"));
//                productDtailBean3.setBrand(jsonObject.getString("brand"));
//                productDtailBean3.setBrandIntroduction(jsonObject.getString("brandIntroduction"));
//                productDtailBean3.setGrade(jsonObject.getString("grade"));
//                productDtailBean3.setLabel(jsonObject.getString("label"));
//
//                //setDataProduct(productDtailBean3, listTypeInfo);
//                //productDataInfo.add(productDtailBean2);
//                listTypeInfo.clear();
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    productDtailBean123 = new ProductDetailBean123();
//                    com.alibaba.fastjson.JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));
//                    productDtailBean123.setNum1(jsonObject1.getString("num1"));
//                    productDtailBean123.setNum2(jsonObject1.getString("num2"));
//                    productDtailBean123.setNum3(jsonObject1.getString("num3"));
//                    productDtailBean123.setUnitPrice1(jsonObject1.getString("unitPrice1"));
//                    productDtailBean123.setUnitPrice2(jsonObject1.getString("unitPrice2"));
//                    productDtailBean123.setUnitPrice3(jsonObject1.getString("unitPrice3"));
//                    productDtailBean123.setGoodsTypeName(jsonObject1.getString("goodsTypeName"));
//                    productDtailBean123.setGoodsId(jsonObject1.getString("goodsId"));
//
//                    listTypeInfo.add(productDtailBean123);
//                }
//
//                System.out.print("ldjgfdewfoidjspkfjfjoiuj89reoijfjsoifjjego" + listTypeInfo.size());
//               // setDataProduct(productDtailBean3, listTypeInfo);
//
//                listDataInfo.clear();
//                for (int i = 0; i < jsonArray1.size(); i++) {
//                    productDtailBean1 = new ProductDtailBean();
//                    com.alibaba.fastjson.JSONObject jsonObject2 = JSON.parseObject(String.valueOf(jsonArray1.get(i)));
//                    productDtailBean1.setName(jsonObject2.getString("name"));
//                    productDtailBean1.setValue(jsonObject2.getString("value"));
//
//                    listDataInfo.add(productDtailBean1);
//                }
//                System.out.print("请求产品详情数据成功+++++++++" + listDataInfo.get(0).getName());
//            }
//
//
//
//            @Override
//            public void onFailure(HttpException error, String msg) {
//
//            }
//        });
//    }

    private void initData() {
        imageviews.clear();
        imageviews.add(R.drawable.tupian_03);
        imageviews.add(R.drawable.tupian_06_06);
        imageviews.add(R.drawable.tupian_03);
        imageviews.add(R.drawable.tupian_06_06);
        imageviews.add(R.drawable.tupian_03);
    }

    //二维码返回结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 0x100) {
                String result = data.getStringExtra("result");
                handleResult(result);
            }
        }
    }

    //二维码扫描结果判断
    private void handleResult(String result) {
        //什么也没有
        if (TextUtils.isEmpty(result)) {
            //Intent intent=new Intent(getActivity(),)
            Toast.makeText(getActivity(), "小绿什么都没得到呢！！！", Toast.LENGTH_SHORT).show();
            return;
        }
        //网址
        if (result.startsWith("http://") || result.startsWith("http://") || result.startsWith("https://")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else if (result.startsWith("tel:")) {
            //电话
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else if (result.startsWith("smsto:")) {
            //短信
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else if (result.startsWith("mailto:")) {
            //邮件
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else if (result.startsWith("market:")) {
            //应用市场
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }
    }


    private class A extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = list.get(position);
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(list.get(position));
            //container.addView();
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}


