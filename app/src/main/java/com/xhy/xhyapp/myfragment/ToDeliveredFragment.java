package com.xhy.xhyapp.myfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ZKKToDeliveredAdapter;
import com.xhy.xhyapp.myactivity.VariousOrdersActivity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDeliveredFragment extends Fragment {

    private List<VariousOrdersActivity> list=new ArrayList();
    private  VariousOrdersActivity variousOrdersActivity=null;


    private ListView listview_todelivered;
    private List<Integer> images=new ArrayList<Integer>();
    public ToDeliveredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_delivered, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview_todelivered = ((ListView) view.findViewById(R.id.listView_todelivered));

        list.clear();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/MerchantOrder/GetMerchantOrder?merchantId=1&pageIndex=1&pageSize=30&orderState=2", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Toast.makeText(getActivity(),responseInfo.result,Toast.LENGTH_SHORT).show();
                //String resultq = responseInfo.result;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    JSONArray jsonArray = jsonObject.getJSONArray("orderList");
                    //System.out.print("========"+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        variousOrdersActivity = new VariousOrdersActivity();
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        // System.out.print("========"+jsonObject1+"+++++++++");

                        variousOrdersActivity.setExpressPrice(jsonObject1.getString("expressPrice"));
                        variousOrdersActivity.setGoodsName(jsonObject1.getString("goodsName"));
                        variousOrdersActivity.setGoodsNumber(jsonObject1.getString("goodsNumber"));
                        variousOrdersActivity.setIsCondirm(jsonObject1.getString("isConfirm"));
                        variousOrdersActivity.setIsRemind(jsonObject1.getString("isRemind"));
                        variousOrdersActivity.setThumbnailImg(jsonObject1.getString("thumbnailImg"));
                        variousOrdersActivity.setTotalMoney(jsonObject1.getString("totalMoney"));
                        variousOrdersActivity.setUnitPrice(jsonObject1.getString("unitPrice"));
                        variousOrdersActivity.setOrderId(jsonObject1.getInt("orderId"));
                        variousOrdersActivity.setIsRemind(jsonObject1.getString("isRemind"));
                        list.add(variousOrdersActivity);

                    }
                    //Toast.makeText(getActivity(),list.toString(),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //CompleteAdapter completeAdapter=new CompleteAdapter(getActivity(),images);
                ZKKToDeliveredAdapter zkkToDeliveredAdapter = new ZKKToDeliveredAdapter(getActivity(), list);
                listview_todelivered.setAdapter(zkkToDeliveredAdapter);


            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("--------------------错误");
                Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
            }

        });

    }


}
