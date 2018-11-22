package com.xhy.xhyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.adapter.HomeSearchAdapter;
import com.xhy.xhyapp.bean.CategoryList;
import com.xhy.xhyapp.purchaseactivity.ProductDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout ll_cancel;
    private TextView search_txt_sousuo;
    String name;
    private EditText editText;
    private ListView search_listview;
    private LinearLayout ll_sousuo;

    List<CategoryList> cateListData = new ArrayList<>();
    CategoryList categoryList;
    HomeSearchAdapter homeSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ll_cancel = ((LinearLayout) findViewById(R.id.ll_cancel));
        ll_sousuo = ((LinearLayout) findViewById(R.id.ll_sousuo));
        search_txt_sousuo = ((TextView) findViewById(R.id.search_txt_sousuo));
        editText = ((EditText) findViewById(R.id.editText));
        search_listview = ((ListView) findViewById(R.id.search_listview));

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        editText.setText(name);
        editText.setSelection(editText.getText().length());

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.length() == 0) {
                    Toast.makeText(SearchActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils httpUtils = new HttpUtils();
                            httpUtils.send(HttpRequest.HttpMethod.POST,
                                    "http://139.196.234.104:8000/appapi/Goods/GetGoods?merchantId=1&goodsCactoryId=-1&goodsName=" + name + "&pageSize=100&pageIndex=1&isRelease=1",
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            String result = responseInfo.result;
                                            try {
                                                JSONObject json = new JSONObject(result);
                                                JSONArray goodsList = json.getJSONArray("goodsList");
                                                cateListData.clear();
                                                for (int i = 0; i < goodsList.length(); i++) {
                                                    JSONObject jsonObject = goodsList.getJSONObject(i);
                                                    String thumbnailImg = jsonObject.getString("thumbnailImg");
                                                    String goodsName = jsonObject.getString("goodsName");
                                                    String goodsType = jsonObject.getString("goodsType");
                                                    String minNum = jsonObject.getString("minNum");
                                                    String minPrice = jsonObject.getString("minPrice");
                                                    String sumStock = jsonObject.getString("sumStock");
                                                    String goodsStyleType = jsonObject.getString("goodsStyleType");
                                                    String goodsId = jsonObject.getString("goodsId");


                                                    categoryList = new CategoryList();
                                                    categoryList.setGoodsName(goodsName);
                                                    categoryList.setGoodsType(goodsType);
                                                    categoryList.setMinNum(minNum);
                                                    categoryList.setMinPrice(minPrice);
                                                    categoryList.setSumStock(sumStock);
                                                    categoryList.setThumbnailImg(thumbnailImg);
                                                    categoryList.setGoodsStyleType(goodsStyleType);
                                                    categoryList.setGoodsId(goodsId);

                                                    cateListData.add(categoryList);

                                                }
                                                homeSearchAdapter.notifyDataSetChanged();
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
                    homeSearchAdapter = new HomeSearchAdapter(SearchActivity.this, cateListData);
                    search_listview.setAdapter(homeSearchAdapter);

                    search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                            intent.putExtra("merchantId", "1");
                            intent.putExtra("goodsId", cateListData.get(i).getGoodsId());
                            intent.putExtra("goodsStyleType", cateListData.get(i).getGoodsStyleType());
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
