package com.xhy.xhyapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ShangpinThreeRightShangAdapter;
import com.xhy.xhyapp.adapter.ShangpinThreeRightXiaAdapter;
import com.xhy.xhyapp.bean.ShangpinBean;
import com.xhy.xhyapp.view.Fragment_shangpin_three_PopubWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Fragment_shangpin_three extends Fragment implements View.OnClickListener{
    TextView shangjia,xiajia,fabu;
    Button home;
    OnButtonClick onButtonClick;
    OnHomeClick onHomeClick;
    List<ShangpinBean> listBeanS,listBeanX;
    ListView listView;
    ShangpinThreeRightShangAdapter adapter1;
    ShangpinThreeRightXiaAdapter adapter2;
    Fragment_shangpin_three_PopubWindow pop;
    private Handler handler;
    String pathcaigou = "";
    private int sta ;
    String result;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shangpin_three, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shangjia = ((TextView) view.findViewById(R.id.fragment_shangpin_three_shangjia));shangjia.setOnClickListener(this);
        xiajia = ((TextView) view.findViewById(R.id.fragment_shangpin_three_xiajia));xiajia.setOnClickListener(this);
        fabu = ((TextView) view.findViewById(R.id.fragment_shangpin_three_fabu));fabu.setOnClickListener(this);
        home = ((Button) view.findViewById(R.id.shangpin_three_home));home.setOnClickListener(this);

        listBeanS = new ArrayList<>();
        listBeanX = new ArrayList<>();

        listView = ((ListView) view.findViewById(R.id.shangpin_three_regiht));//初始化listview
        shangjia.setTextColor(0xff49C49E);//初始化数据
        xiajia.setTextColor(0xff5a5a5a);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        fast();
                        adapter1.notifyDataSetChanged();
                        break;
                    case 1:
                        fast();
                        adapter2.notifyDataSetChanged();
                        break;
                    case 2:
                        listBeanX.remove(msg.arg1);
                        System.out.println("----------------"+msg.arg1);
                        adapter2.notifyDataSetChanged();
                        break;
                    case 3: // 已上架商品 下架
                        listBeanS.remove(msg.arg1);
                        System.out.println("----------------"+msg.arg1);
                        adapter1.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };

        adapter1 = new ShangpinThreeRightShangAdapter(getContext(),listBeanS,handler);
        adapter2 = new ShangpinThreeRightXiaAdapter(getContext(),listBeanX,handler);
        listView.setAdapter(adapter1);
        DownloadMy(0);
    }


    @Override
    public void onClick(View view) {//点击事件
        switch (view.getId()){
            case R.id.fragment_shangpin_three_shangjia://上架
                shangjia.setTextColor(0xff49C49E);
                xiajia.setTextColor(0xff5a5a5a);
                listView.setAdapter(adapter1);
                DownloadMy(0);
                adapter1.notifyDataSetChanged();
                break;
            case R.id.fragment_shangpin_three_xiajia://下架
                xiajia.setTextColor(0xff49C49E);
                shangjia.setTextColor(0xff5a5a5a);
                listView.setAdapter(adapter2);
                DownloadMy(1);
                adapter2.notifyDataSetChanged();
                break;
            case R.id.fragment_shangpin_three_fabu://发布
                if (onButtonClick != null){
                    onButtonClick.onClick(fabu);
                }
                break;
            case R.id.shangpin_three_home://返回home
                if (onHomeClick != null){
                    onHomeClick.onClick(home);
                }
                break;
            default:
                break;
        }
    }

    private void DownloadMy(int a) {
        sta = a;;
        pathcaigou = "http://139.196.234.104:8000/appapi/Merchant/GetMerchantGoods?merchantId="+1+"&pageSize=30&pageIndex=1&merchantGoodsState="+a;
        new Thread(new Runnable() {
            @Override       //
            public void run() {
                HttpUtils httputils1 = new HttpUtils();
                httputils1.configCurrentHttpCacheExpiry(300);
                httputils1.send(HttpRequest.HttpMethod.GET, pathcaigou,
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                result = responseInfo.result;
                                System.out.println("-----="+result);
                                if (result.length()>0){
                                    Message me = new Message();
                                    me.what = sta;
                                    handler.sendMessage(me);
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                System.out.println("--------------------------faliue");
                            }
                        });
            }
        }).start();
    }



    private void fast() {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jsonArray = json.getJSONArray("merchantGoodsList");

            if (sta == 0){
                int leng = listBeanS.size();
                for (int i=0;i<leng;i++){
                    listBeanS.remove(0);
                }
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ShangpinBean bean = JSON.parseObject(jsonObject.toString(),ShangpinBean.class);
                    listBeanS.add(bean);
                }
            }else {
                int lengx = listBeanX.size();
                for (int i=0;i<lengx;i++){
                    listBeanX.remove(0);
                }
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ShangpinBean bean = JSON.parseObject(jsonObject.toString(),ShangpinBean.class);
                    listBeanX.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //button回调接口
    public interface OnButtonClick{
        public void onClick(View view);
    }
    public OnButtonClick getOnButtonClick(){
        return onButtonClick;
    }
    public void setOnButtonClick(OnButtonClick onButtonClick){
        this.onButtonClick = onButtonClick;
    }



    //Home接口回调
    public interface OnHomeClick{
        public void onClick(View view);
    }
    public OnHomeClick getOnHomeClick(){
        return onHomeClick;
    }
    public void setOnHomeClick(OnHomeClick onHomeClick){
        this.onHomeClick = onHomeClick;
    }
}
