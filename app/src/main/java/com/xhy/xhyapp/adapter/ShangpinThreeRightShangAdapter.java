package com.xhy.xhyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ShangpinBean;
import com.xhy.xhyapp.view.Fragment_shangpin_three_PopubWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class ShangpinThreeRightShangAdapter extends BaseAdapter{

    Context context;
    List<ShangpinBean> list,list2;
    Handler handler;
    String merchantId = "1";

    Activity activity;
    Fragment_shangpin_three_PopubWindow pop;
    ImageView finish,pricejian,priceplus,tjian,tplus;
    EditText price,t;
    TextView sure,xianshi;
    String pathXIAJIA = "";
    String pathGAI = "";


    public ShangpinThreeRightShangAdapter(Context context, List<ShangpinBean> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

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
        ViewHolder viewholder;
        if (view == null){
            viewholder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.fragment_shangpin_three_right_shangjia,null);

            Button XiaJia = ((Button) view.findViewById(R.id.fragment_shangpin_three_xia));
            viewholder.XIAJIA=XiaJia;
            Button button1 = ((Button) view.findViewById(R.id.fragment_shangpin_three_bianji));
            viewholder.button1=button1;
            viewholder.image1 = ((ImageView) view.findViewById(R.id.fragment_shangpin_three_image1));
            viewholder.shname = ((TextView) view.findViewById(R.id.fragment_shangpin_three_ShName));

            view.setTag(viewholder);
        }else {
            viewholder= ((ViewHolder) view.getTag());
        }
        viewholder.XIAJIA.setOnClickListener(new XiaJiaOnClic(i));       //删除
        viewholder.button1.setOnClickListener(new Button1Onclic(i));
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(viewholder.image1, list.get(i).getThumbnailImg());
        viewholder.shname.setText(list.get(i).getGoodsName());

        return view;
    }



    class Button1Onclic implements View.OnClickListener{
        private int a;
        float jiage = 13;
        float zhiliang = 13;
        public Button1Onclic(int a) {
            this.a=a;
        }

        @Override
        public void onClick(View view) {
            activity = ((Activity) context);
            pop = new Fragment_shangpin_three_PopubWindow( activity , new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }}, R.layout.fragment_shangpin_three_popubwindow);
            pop.showAtLocation(activity.findViewById(R.id.dianpu_shangpinguanli), Gravity.BOTTOM,0,0);
            View vi = pop.getView();
            ImageView finish = ((ImageView) vi.findViewById(R.id.fragment_shangpin_three_pop_finish));
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });

            price = ((EditText) vi.findViewById(R.id.fragment_shangpin_three_pop_priceedit));
            t = ((EditText) vi.findViewById(R.id.efragment_shangpin_three_pop_tedit));
            xianshi = ((TextView) vi.findViewById(R.id.fragment_shangpin_three_xianshi));

            String s1 = list.get(a).getUnitPrice();
            String s2 = list.get(a).getGoodsNumber();

            if (s1.length()>0){
                float pri =  Float.parseFloat(s1);
                jiage = pri;
            }else {
                jiage = 0;
            }

            if (s2.length()>0){
                float ttt =  Float.parseFloat(s2);
                zhiliang = ttt;
            }else {
                zhiliang = 0;
            }

            price.setText(jiage+"");
            t.setText(zhiliang+"");
            xianshi.setText("￥"+jiage+"元");

            pricejian = ((ImageView) vi.findViewById(R.id.fragment_shangpin_three_pop_pricjian));
            pricejian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ssjiage = price.getText().toString().trim();
                    if (ssjiage.length()>0){
                        float pri =  Float.parseFloat(ssjiage);
                        jiage=pri;
                    }else {
                        jiage=0;
                    }
                    if (jiage>0){
                        jiage -= 1;
                        price.setText(jiage+"");
                        price.setSelection(ssjiage.length(),ssjiage.length());
                        xianshi.setText("￥"+jiage+"元");
                    }
                }
            });
            priceplus = ((ImageView) vi.findViewById(R.id.fragment_shangpin_three_pop_priceplus));
            priceplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ssjiage = price.getText().toString().trim();
                    if (ssjiage.length()>0){
                        float pri =  Float.parseFloat(ssjiage);
                        jiage=pri;
                    }else {
                        jiage=0;
                    }
                    jiage+=1;
                    price.setText(jiage+"");
                    price.setSelection(ssjiage.length(),ssjiage.length());
                    xianshi.setText("￥"+jiage+"元");
                }
            });
            tjian = ((ImageView) vi.findViewById(R.id.fragment_shangpin_three_pop_tjian));
            tjian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sszhiliang = t.getText().toString().trim();
                    if (sszhiliang.length()>0){
                        float ttt =  Float.parseFloat(sszhiliang);
                        zhiliang=ttt;
                    }else {
                        zhiliang=0;
                    }
                    if (zhiliang>0){
                        zhiliang-=1;
                    }else {
                        zhiliang=0;
                    }
                    t.setText(zhiliang+"");
                    t.setSelection(sszhiliang.length(),sszhiliang.length());
                }
            });
            tplus = ((ImageView) vi.findViewById(R.id.fragment_shangpin_three_pop_tplus));
            tplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sszhiliang = t.getText().toString().trim();
                    if (sszhiliang.length()>0){
                        float ttt =  Float.parseFloat(sszhiliang);
                        zhiliang=ttt;
                    }else {
                        zhiliang=0;
                    }
                    zhiliang+=1;
                    t.setText(zhiliang+"");
                    t.setSelection(sszhiliang.length(),sszhiliang.length());
                }
            });
            sure = ((TextView) vi.findViewById(R.id.fragment_shangpin_three_pop_sure));
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  pop.dismiss();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils httpUtils = new HttpUtils();
                            pathGAI = "http://139.196.234.104:8000/appapi/Merchant/EditMerchantGoodsPrice?merchantId="+merchantId+"&goodsId="+list.get(a).getGoodsId()+"&merchantGoodsId="+list.get(a).getMerchantGoodsId()+"&goodsNumber="+t.getText().toString().trim()+"&unitPrice="+price.getText().toString().trim();
                            System.out.println("------gai------"+pathGAI);
                            httpUtils.send(HttpRequest.HttpMethod.GET, pathGAI, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result = responseInfo.result;
                                    try {
                                        JSONObject json = new JSONObject(result);
                                        if ("0".equals(json.getString("state"))){
                                            Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
                                            ShangpinBean sBean = list.get(a);
                                            sBean.setGoodsNumber(t.getText().toString().trim());
                                            sBean.setUnitPrice(price.getText().toString().trim());
                                            list2 = new ArrayList<>();
                                            list2.clear();
                                            for (int z=0;z<list.size();z++){
                                                if (z==a){
                                                    list2.add(sBean);
                                                }else {
                                                    list2.add(list.get(z));
                                                }
                                            }
                                            list.clear();
                                            for (int z=0;z<list2.size();z++){
                                                list.add(list2.get(z));
                                            }
                                            notifyDataSetChanged();
                                            pop.dismiss();
//                                            Message me = new Message();
//                                            me.what=4;
                                        }else {
                                            Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            });
        }
    }

    class XiaJiaOnClic implements View.OnClickListener{
        private int a;
        public XiaJiaOnClic(int a) {
            this.a=a;
        }

        @Override
        public void onClick(View view) {
            pathXIAJIA = "http://139.196.234.104:8000/appapi/Goods/EditMerchantGoodsOff?merchantId="+merchantId+"&goodsId="+list.get(a).getGoodsId()+"&merchantGoodsId="+list.get(a).getMerchantGoodsId();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils http1 = new HttpUtils();
                    http1.send(HttpRequest.HttpMethod.GET, pathXIAJIA, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            System.out.println("------------s--x------"+result);
                            try {
                                JSONObject json = new JSONObject(result);
                                if ("0".equals(json.getString("state"))){
                                    Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
                                    Message me = new Message();
                                    me.what=3;
                                    me.arg1= a;
                                    handler.sendMessage(me);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(context,"下架失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }
    }

    class ViewHolder{
        Button XIAJIA;
        Button button1;
        ImageView image1;
        PopupWindow popwin;
        TextView shname;

    }
}
