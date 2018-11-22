package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ContractTitleBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HetongActivity extends AppCompatActivity {

    ImageView Ht_back;
    ListView Ht_listview;
    List<ContractTitleBean> list;
    Handler handler;
    String result;
    HtAdapter adaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hetong);
        Ht_back = ((ImageView) findViewById(R.id.Ht_back));
        Ht_listview = ((ListView) findViewById(R.id.Ht_listview));

        list = new ArrayList<>();

        Ht_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        try {
                            JSONObject json = new JSONObject(result);
                            if ("0".equals(json.getString("state"))){
                                JSONArray jsonArray = json.getJSONArray("articleList");
                                for (int a = 0 ; a<jsonArray.length();a++){
                                    ContractTitleBean bean = new ContractTitleBean();
                                    JSONObject js = jsonArray.getJSONObject(a);
                                    bean.setContractRitle(js.getString("contractTitle"));
                                    bean.setId(js.getString("id"));
                                    bean.setAddTime(js.getString("addTime"));
                                    bean.setUrl(js.getString("url"));
                                    list.add(bean);
                                }
                                System.out.println("---------------------hbcg");
                                adaper = new HtAdapter();
                                Ht_listview.setAdapter(adaper);

                            }else {
                                Toast.makeText(getBaseContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://139.196.234.104:8000/appapi/other/GetContract?merchantId=1&pageIndex=1&pageSize=30";
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, path, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        result = responseInfo.result;
                        Message me = new Message();
                        me.what = 0;
                        handler.sendMessage(me);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getBaseContext(),"获取合同管理失败",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }).start();
    }

    private class HtAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewholder = null;
            if (view == null){
                if ("商品采购合同".equals(list.get(i).getContractRitle())){
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_hetong_item2,null);
                    viewholder = new ViewHolder();
                    viewholder.re = ((RelativeLayout) view.findViewById(R.id.my_HTht2));
                    viewholder.hetong = ((TextView) view.findViewById(R.id.my_ht2_hetong));
                    viewholder.time = ((TextView) view.findViewById(R.id.my_ht2_time));
                    viewholder.goodsname = ((TextView) view.findViewById(R.id.my_ht2_goodsname));
                    viewholder.goodstock = ((TextView) view.findViewById(R.id.my_ht2_goodstock));
                    viewholder.detal = ((TextView) view.findViewById(R.id.my_ht2_orderdetal));

                    view.setTag(viewholder);
                }else {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_hetong_item1,null);
                    viewholder = new ViewHolder();
                    viewholder.re = ((RelativeLayout) view.findViewById(R.id.my_HTht1));
                    viewholder.hetong = ((TextView) view.findViewById(R.id.my_ht1_hetong));
                    viewholder.time = ((TextView) view.findViewById(R.id.my_ht1_time));

                    view.setTag(viewholder);
                };
            }else {
                viewholder = ((ViewHolder) view.getTag());
            }

            if ("平台入驻合同".equals(list.get(i).getContractRitle())){
                viewholder.hetong.setText(list.get(i).getContractRitle());
                viewholder.time.setText(list.get(i).getAddTime());

                viewholder.re.setOnClickListener(new OnTickClick(i){
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(HetongActivity.this,HeTongDetalActivity.class);
                        it.putExtra("url",list.get(m).getUrl());
                        it.putExtra("name",list.get(m).getContractRitle());
                        startActivity(it);
                    }
                });
            }else {
                viewholder.hetong.setText(list.get(i).getContractRitle());
                viewholder.time.setText(list.get(i).getAddTime());

                viewholder.re.setOnClickListener(new OnTickClick(i){
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(HetongActivity.this,HeTongDetalActivity.class);
                        it.putExtra("url",list.get(m).getUrl());
                        it.putExtra("name",list.get(m).getContractRitle());
                        startActivity(it);
                    }
                });
            }
            return view;
        }
    }

    class OnTickClick implements View.OnClickListener{
        int m;

        public OnTickClick(int m) {
            this.m = m;
        }

        @Override
        public void onClick(View view) {

        }
    }

    class ViewHolder{
        RelativeLayout re;
        TextView detal,hetong,time,goodsname,goodstock;
    }
}
