package com.xhy.xhyapp.purchaseactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.xhy.xhyapp.HomeSearchActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyCategoryListAdapter;
import com.xhy.xhyapp.bean.CategoryList;
import com.xhy.xhyapp.content.ContentUrl;
import com.xhy.xhyapp.homepageactivity.CollectActivity;

import java.util.ArrayList;
import java.util.List;

public class InportFruitActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout image_inport_fang, image_inport_start;
    private ListView lv_inport;
    private List<Integer> images = new ArrayList<>();
    private LinearLayout ll_back;
    private List<CategoryList> cateListData = new ArrayList<>();
    private CategoryList categoryList;
    private Context context;
    private String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inport_fruit);

        context = InportFruitActivity.this;
        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("categoryId");
        categoryName = intent.getStringExtra("categoryName");
        initview();
        requestData(categoryId);

    }

    private void requestData(String categoryId) {

        HttpUtils httpUtils = new HttpUtils();

        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("goodsCactoryId", categoryId);
        requestParams.addQueryStringParameter("goodsName", "");
        requestParams.addQueryStringParameter("pageSize", "1000");
        requestParams.addQueryStringParameter("pageIndex", "1");
        requestParams.addQueryStringParameter("isRelease", "0");

        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.CATEGORY_LIST_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print("第二次请求数据成功+++++++++" + json);
                Toast.makeText(InportFruitActivity.this, "第二次请求数据成功", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("goodsList");

                cateListData.clear();
                for (int i = 0; i < jsonArray.size(); i++) {
                    categoryList = new CategoryList();
                    JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));

                    categoryList.setGoodsName(jsonObject1.getString("goodsName"));
                    categoryList.setThumbnailImg(jsonObject1.getString("thumbnailImg"));

                    categoryList.setStock(jsonObject1.getString("stock"));
                    categoryList.setMinNum(jsonObject1.getString("minNum"));
                    categoryList.setMinPrice(jsonObject1.getString("minPrice"));
                    categoryList.setGoodsId(jsonObject1.getString("goodsId"));
                    categoryList.setCollectionId(jsonObject1.getString("collectionId"));
                    categoryList.setGoodsStyleType(jsonObject1.getString("goodsStyleType"));
                    categoryList.setSumStock(jsonObject1.getString("sumStock"));

                    cateListData.add(categoryList);
                }
//                System.out.print("第二次请求数据成功+++++++++王玉奎"+cateListData.get(0).getGoodsName());
                MyCategoryListAdapter myCateListAdapter = new MyCategoryListAdapter(context, cateListData);
                lv_inport.setAdapter(myCateListAdapter);

                lv_inport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent=new Intent(InportFruitActivity.this,ProductDetailActivity.class);

                        intent.putExtra("merchantId","1");
                        intent.putExtra("goodsId",cateListData.get(i).getGoodsId());
                        intent.putExtra("goodsStyleType",cateListData.get(i).getGoodsStyleType());

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(InportFruitActivity.this, "第二次请求数据失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initview() {

        tv_title = (TextView) findViewById(R.id.tv_title);

        image_inport_fang = (LinearLayout) findViewById(R.id.image_inport_fang);
        image_inport_start = (LinearLayout) findViewById(R.id.image_inport_start);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));

        lv_inport = (ListView) findViewById(R.id.lv_inport);

        tv_title.setText(categoryName);

        ll_back.setOnClickListener(this);

        image_inport_fang.setOnClickListener(this);
        image_inport_start.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.image_inport_fang:
                Intent intent = new Intent(InportFruitActivity.this, HomeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.image_inport_start:
                Intent intent1 = new Intent(InportFruitActivity.this, CollectActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
