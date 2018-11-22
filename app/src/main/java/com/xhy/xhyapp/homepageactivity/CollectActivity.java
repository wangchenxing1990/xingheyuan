package com.xhy.xhyapp.homepageactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.xhy.xhyapp.adapter.MyCollectionAdapter;
import com.xhy.xhyapp.bean.CollectionBean;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {
    //返回按钮
    private LinearLayout collect_back;
    //listview的条目
    private ListView lv_collect;
    //listview的数据源
    private List<Integer> images=new ArrayList<>();
    private List<CollectionBean> collectionData = new ArrayList<>();
    private CollectionBean collectionBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        requestData();
    }

    private void requestData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("merchantid", "1");
        requestParams.addQueryStringParameter("pageSize", "100");
        requestParams.addQueryStringParameter("pageIndex", "1");
        httpUtils.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.COLLECTION_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print("请求收藏数据成功========" + json);
                Toast.makeText(CollectActivity.this, "收藏请求数据成功", Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Collection");

                collectionData.clear();
                for (int i = 0; i < jsonArray.size(); i++) {
                    collectionBean = new CollectionBean();
                    JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));
                    collectionBean.setGoodsName(jsonObject1.getString("goodsName"));
                    collectionBean.setStock(jsonObject1.getString("stock"));
                    collectionBean.setThumbnailImg(jsonObject1.getString("thumbnailImg"));
                    collectionBean.setGoodsId(jsonObject1.getString("goodsId"));
                    collectionData.add(collectionBean);
                }
                //System.out.print("请求收藏数据成功========"+collectionData.get(0).getGoodsName());
                lv_collect.setAdapter(new MyCollectionAdapter(CollectActivity.this, collectionData));

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.print("请求收藏数据失败+++++++++++++");
                Toast.makeText(CollectActivity.this, "收藏请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView() {
        collect_back= (LinearLayout) findViewById(R.id.collect_back);
        lv_collect= (ListView) findViewById(R.id.lv_collect);
        collect_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
