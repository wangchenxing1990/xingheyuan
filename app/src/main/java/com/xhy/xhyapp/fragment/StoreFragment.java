package com.xhy.xhyapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.StoreAdapter;
import com.xhy.xhyapp.myactivity.Servicecenter;
import com.xhy.xhyapp.storeactivity.RegisterStoreActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private View inflate;
    private Button btn_sure;
    private ListView kaidianxuzhi;
    private List<String> list=new ArrayList<>();

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate=  inflater.inflate(R.layout.fragment_store, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kaidianxuzhi=(ListView) inflate.findViewById(R.id.kaidianxuzhi);
        list.clear();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/other/GetServerCenterArticle?merchantId=1&pageSize=20&pageIndex=1", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Toast.makeText(getActivity(),responseInfo.result,Toast.LENGTH_SHORT).show();
                //String resultq = responseInfo.result;

                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    JSONArray jsonArray = jsonObject.getJSONArray("articleList");
                    //System.out.print("========"+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        // System.out.print("========"+jsonObject1+"+++++++++");

                        list.add(jsonObject1.getString("articleTitle"));
                    }
                    Log.e("#####aaa######",list.toString());
                    //Toast.makeText(getActivity(),list.toString(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                StoreAdapter storeAdapter = new StoreAdapter(getActivity(), list);
                kaidianxuzhi.setAdapter(storeAdapter);


                btn_sure = ((Button) inflate.findViewById(R.id.but_sure));
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), RegisterStoreActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("--------------------错误");
                Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
