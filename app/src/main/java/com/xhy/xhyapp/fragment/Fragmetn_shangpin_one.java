package com.xhy.xhyapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.Category;
import com.xhy.xhyapp.bean.CategoryList;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Fragmetn_shangpin_one extends Fragment {

    ListView listViewleft, listViewright;
    OnButtonClick onButtonClick;
    OnHomeClick onHomeClick;
    LeftAdapter adapter1;
    RightAdapter adapter2;
    Button sure, home;
    TextView quanxuan;
    int itemNUM = 0;
    private String caId;
    private Category category;
    List<Category> listLeft;
    List<CategoryList> listRight;
    Map<Integer,String> map;
    String pathitem;
    String itemcatch = "";
    Handler handler;
    CallBackValue callBackValue;
    boolean mapState = false;
//    Map<String,List<CategoryList>> mapright;


//    public Fragmetn_shangpin_one(Handler handler) {
//        this.handler1 = handler;
//    }

    public Fragmetn_shangpin_one() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = ((CallBackValue) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shangpin_one, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        listViewleft.setAdapter(adapter1);
                        requestData(listLeft.get(0).categoryId);
                        listViewright.setAdapter(adapter2);
                        break;
                    case 1:
                        adapter2.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };

        //初始化数据

        listLeft = new ArrayList<>();
        listRight = new ArrayList<>();
        map = new ArrayMap<>();

        listViewleft = ((ListView) view.findViewById(R.id.shangpin_one_left));
        listViewright = ((ListView) view.findViewById(R.id.shangpin_one_regiht));
        adapter1 = new LeftAdapter(getContext());//左shipeiqi
        adapter2 = new RightAdapter(getContext());

        quanxuan = ((TextView) getActivity().findViewById(R.id.iv_shop_quanxuan));

        sure = ((Button) view.findViewById(R.id.shangpin_one_sure));//确定按钮
        sure.setOnClickListener(new View.OnClickListener() {//button点击事件
            // @Override
            public void onClick(View view) {
                if (mapState){
                    if (map.size()>0){
                        itemcatch = "";
                        for (Integer h : map.keySet()){
                            itemcatch = itemcatch+h+",";
                        }
                    }
                    callBackValue.SendMessageValue(pathitem,itemcatch,caId);
                    if (onButtonClick != null) {
                        onButtonClick.onClick(sure);
                    }
                }
            }
        });
        home = ((Button) view.findViewById(R.id.shangpin_one_home));//home按钮
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onHomeClick != null) {
                    onHomeClick.onClick(home);
                }
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.GET, ContentUrl.BASE_URL + ContentUrl.CATEGORY_URL + "?merchantId=1", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        System.out.print("---------json--------" + json);
                        if (json.length()>0){
                            // Toast.makeText(getActivity(),"数据请求成功",Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = JSON.parseObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("categoryList");
                            for (int i = 0; i < jsonArray.size(); i++) {
                                category = new Category();
                                JSONObject jsonob = JSON.parseObject(String.valueOf(jsonArray.get(i)));
                                category.setCategoryName((String) jsonob.get("categoryName"));
                                //category.setIcon((String) jsonob.get("icon"));
                                category.setCategoryId((String) jsonob.get("categoryId"));
                                listLeft.add(category);
                            }
                            Message me = new Message();
                            me.what = 0;
                            handler.sendMessage(me);
                        }
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.print("----------------------数据请求是失败+++++++++");
                        Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();


        //左适配器item监听事件
        listViewleft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (itemNUM != i){
                    itemNUM = i;
                    adapter1.notifyDataSetChanged();
                    quanxuan.setText("全选");
                    map.clear();
                    requestData(listLeft.get(i).categoryId);
                }
            }
        });


        //全部按钮点击变图片
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("全选".equals(quanxuan.getText())) {
                    quanxuan.setText("取消");
                    for (int i=0;i<listLeft.size();i++){
                        map.put(i,"true");
                    }
                    mapState = true;
                    adapter2.notifyDataSetChanged();
                } else {
                    quanxuan.setText("全选");
                    for (int i=0;i<listLeft.size();i++){
                        map.put(i,"false");
                    }
                    mapState = false;
                    adapter2.notifyDataSetChanged();
                }
            }
        });
    }

    private void requestData(String categoryId) {
        caId = categoryId;
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.configCurrentHttpCacheExpiry(0);
        requestParams.addQueryStringParameter("merchantId", "1");
        requestParams.addQueryStringParameter("goodsCactoryId", categoryId);
        requestParams.addQueryStringParameter("goodsName", "");
        requestParams.addQueryStringParameter("pageSize", "2");
        requestParams.addQueryStringParameter("pageIndex", "1");
        requestParams.addQueryStringParameter("isRelease", "1  ");
        pathitem = ContentUrl.BASE_URL + ContentUrl.CATEGORY_LIST_URL;
        httpUtils.send(HttpRequest.HttpMethod.POST,pathitem, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                if (json.length()>0){
                    JSONObject jsonObject = JSON.parseObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("goodsList");
                    int qq = listRight.size();
                    for (int q = 0;q<qq;q++){
                        listRight.remove(0);
                    }
                    adapter2.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        CategoryList categoryList = new CategoryList();
                        JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(i)));
                        categoryList.setGoodsName(jsonObject1.getString("goodsName"));
                        categoryList.setThumbnailImg(jsonObject1.getString("thumbnailImg"));

                        categoryList.setStock(jsonObject1.getString("stock"));
                        categoryList.setMinNum(jsonObject1.getString("m   inNum"));
                        categoryList.setMinPrice(jsonObject1.getString("minPrice"));
                        categoryList.setGoodsId(jsonObject1.getString("goodsId"));
                        categoryList.setCollectionId(jsonObject1.getString("CollectionId"));

                        listRight.add(categoryList);
                    }
                    Message me = new Message();
                    me.what = 1;
                    handler.sendMessage(me);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(getContext(), "第二次请求数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //button回调接口
    public interface OnButtonClick {//button点击回调接口
        public void onClick(View view);
    }
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }


    //Home接口回调
    public interface OnHomeClick {
        public void onClick(View view);
    }
    public OnHomeClick getOnHomeClick() {
        return onHomeClick;
    }
    public void setOnHomeClick(OnHomeClick onHomeClick) {
        this.onHomeClick = onHomeClick;
    }

    //f1 向 f2 经过activity 接口
    public interface CallBackValue {
        public void SendMessageValue(String arg0, String arg1, String arg2);
    }

    private class LeftAdapter extends BaseAdapter {//左适配器
        private Context context;

        public LeftAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return listLeft.size();
        }
        @Override
        public Object getItem(int i) {
            return listLeft.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_shangpin_one_left, viewGroup, false);
            TextView textView = ((TextView) view.findViewById(R.id.fragment_shangpin_one_left_item));
            textView.setText(listLeft.get(i).getCategoryName());
            if (i == itemNUM) {
                textView.setTextColor(0xff49C49E);
            } else {
                textView.setTextColor(0xff5a5a5a);
            }
            return view;
        }
    }


    private class RightAdapter extends BaseAdapter {//右适配器
        Context context;
        public RightAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return listRight.size();
        }
        @Override
        public Object getItem(int i) {
            return listRight.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_shangpin_one_right, null);
            ImageView imageView1 = ((ImageView) view.findViewById(R.id.shangpin_one_ritemimage));
            TextView textView = ((TextView) view.findViewById(R.id.shangpin_one_ritemtext));
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            bitmapUtils.display(imageView1, listRight.get(i).getThumbnailImg());
            textView.setText(listRight.get(i).getGoodsName());

            final ImageView imageView = ((ImageView) view.findViewById(R.id.fragment_shangpin_one_right_buttonxuanze));

            if ("true".equals(map.get(i))) {
                imageView.setImageResource(R.drawable.shangpin_06);
            } else {
                imageView.setImageResource(R.drawable.shangpin_09);
            }
            imageView.setOnClickListener(new ImageOclick(i) {
                @Override
                public void onClick(View view) {
                    if ("true".equals(map.get(i))) {
                        imageView.setImageResource(R.drawable.shangpin_09);
                        map.put(i,"false");
                        quanxuan.setText("全选");
                        int COUNT = 0;
                        for (int e=0;e<listRight.size();e++){
                            if ("true".equals(map.get(e))){
                                COUNT = COUNT+1;
                            }
                        }
                        if (COUNT==0){
                            sure.setBackgroundColor(0xffEEEEEE);
                            mapState = false;
                        }else {
                            sure.setBackgroundColor(0xff4EC6A1);
                            mapState = true;
                        }
                    } else {
                        imageView.setImageResource(R.drawable.shangpin_06);
                        map.put(i,"true");
                        int COUNTtrue = 0;
                        for (int e=0;e<listRight.size();e++){
                            if ("true".equals(map.get(e))){
                                COUNTtrue = COUNTtrue+1;
                            }
                        }
                        if (COUNTtrue == listRight.size()){
                            quanxuan.setText("取消");
                        }else {
                            quanxuan.setText("全选");
                        }
                        if (COUNTtrue==0){
                            sure.setBackgroundColor(0xffEEEEEE);
                            mapState = false;
                        }else {
                            sure.setBackgroundColor(0xff4EC6A1);
                            mapState = true;
                        }
                    }
                }
            });
            return view;
        }
        private class ImageOclick implements View.OnClickListener {
            int i;
            public ImageOclick(int i) {
                this.i=i;
            }
            @Override
            public void onClick(View view) {
            }
        }
    }
}
