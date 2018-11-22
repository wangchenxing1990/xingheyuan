package com.xhy.xhyapp.myfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.CompleteAdapter;
import com.xhy.xhyapp.bean.CompleteBean;
import com.xhy.xhyapp.myactivity.MyPurchaseActivity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFragment extends Fragment {

    private final MyPurchaseActivity myPurchaseActivity;
    private ListView listview_complete;
    private List<Integer> images = new ArrayList<Integer>();
    List<CompleteBean> completeBean = new ArrayList<>();
    CompleteBean bean01=null;
    CompleteAdapter completeAdapter;
    public CompleteFragment(MyPurchaseActivity myPurchaseActivity) {
        this.myPurchaseActivity=myPurchaseActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_complete, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview_complete = ((ListView) view.findViewById(R.id.listView_complete));
        //      completeBean.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ViewUtils.inject(this, view);
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST,
                        "http://139.196.234.104:8000/appapi/MerchantOrder/GetMerchantOrder?merchantId=1&pageIndex=1&pageSize=30&orderState=-100",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                System.out.print("请求订单详情信息的数据成功"+result);
                                try {
                                    JSONObject json = new JSONObject(result);
                                    String state=json.getString("state");
                                    if ("0".equals(state)){
                                        Toast.makeText(getActivity(),"请求数据成功",Toast.LENGTH_SHORT).show();
                                    }
                                    JSONArray orderList = json.getJSONArray("orderList");
                                    completeBean.clear();
                                    for (int i = 0; i < orderList.length(); i++) {
                                        JSONObject json01 = orderList.getJSONObject(i);
                                        String goodsName = json01.getString("goodsName");
                                        String totalMoney = json01.getString("totalMoney");
                                        String unitPrice = json01.getString("unitPrice");
                                        String expressPrice = json01.getString("expressPrice");
                                        String image=json01.getString("thumbnailImg");
                                        String goodsNumber=json01.getString("goodsNumber");
                                        String orderState=json01.getString("orderState");
                                        String orderId=json01.getString("orderId");

                                        bean01 = new CompleteBean();
                                        bean01.setGoodsName(goodsName);
                                        bean01.setExpressPrice(expressPrice);
                                        bean01.setTotalMoney(totalMoney);
                                        bean01.setUnitPrice(unitPrice);
                                        bean01.setGoodsNumber(goodsNumber);
                                        bean01.setThumbnailImg(image);
                                        bean01.setOrderState(orderState);
                                        bean01.setOrderId(orderId);
                                        completeBean.add(bean01);
                                    }

                                    completeAdapter =new CompleteAdapter(myPurchaseActivity,completeBean);
                                    listview_complete.setAdapter(completeAdapter);
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
    }
}
