package com.xhy.xhyapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.HomeSearchActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyCategoryAdapter;
import com.xhy.xhyapp.bean.Category;
import com.xhy.xhyapp.content.ContentUrl;
import com.xhy.xhyapp.purchaseactivity.InportFruitActivity;
import com.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends Fragment {

    private View inflate;
    private GridView grid_purchase;
    private ImageView image_saoyisao;
    private TextView edit_sousuo;
    private Category category;
    private List<Category> cateDAtas=new ArrayList<>();

    public PurchaseFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.fragment_purchase, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        requestData();
    }

    private void initView() {

        grid_purchase = ((GridView) inflate.findViewById(R.id.gv));
        image_saoyisao = ((ImageView) inflate.findViewById(R.id.img_saoyisao));
        edit_sousuo = ((TextView) inflate.findViewById(R.id.edit_sousuo));

        grid_purchase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), InportFruitActivity.class);
                intent.putExtra("categoryId",cateDAtas.get(i).getCategoryId());
                intent.putExtra("categoryName",cateDAtas.get(i).getCategoryName());
                startActivity(intent);
            }
        });
        edit_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_sousuo.setInputType(InputType.TYPE_NULL);
                Intent intent=new Intent(getActivity(), HomeSearchActivity.class);
                startActivity(intent);
            }
        });
        image_saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CaptureActivity.class);

                startActivityForResult(intent,0x100);
            }
        });
    }


    /**
     * 联网请求数据
     */
    private void requestData() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.GET, ContentUrl.BASE_URL + ContentUrl.CATEGORY_URL + "?merchantId=1", new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        System.out.print("数据请求成功+++++++++" + json);
                        // Toast.makeText(getActivity(),"数据请求成功",Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = JSON.parseObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("categoryList");
                        cateDAtas.clear();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            category = new Category();
                            JSONObject jsonob = JSON.parseObject(String.valueOf(jsonArray.get(i)));
                            category.setCategoryName((String) jsonob.get("categoryName"));
                            category.setIcon((String) jsonob.get("icon"));
                            category.setCategoryId((String) jsonob.get("categoryId"));
                            cateDAtas.add(category);
                        }

                        MyCategoryAdapter myCataAdapter = new MyCategoryAdapter(getActivity(), cateDAtas);
                        grid_purchase.setAdapter(myCataAdapter);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.print("数据请求是失败+++++++++");
                        Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
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
        if (TextUtils.isEmpty(result)){
            return;
        }
        //网址
        if (result.startsWith("http://")||result.startsWith("http://")||result.startsWith("https://")){
            Intent intent =new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }else if (result.startsWith("tel:")){
            //电话
            Intent intent =new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }else if (result.startsWith("smsto:")){
            //短信
            Intent intent =new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }else if (result.startsWith("mailto:")) {
            //邮件
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }else if (result.startsWith("market:")) {
            //应用市场
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }
    }
}
