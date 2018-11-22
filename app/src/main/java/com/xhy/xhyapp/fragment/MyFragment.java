package com.xhy.xhyapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.homepageactivity.CollectActivity;
import com.xhy.xhyapp.myactivity.AboutPlatformActivity;
import com.xhy.xhyapp.myactivity.AccountManagementActivity;
import com.xhy.xhyapp.myactivity.CommonProblemActivity;
import com.xhy.xhyapp.myactivity.HetongActivity;
import com.xhy.xhyapp.myactivity.KeHuZiLiaoActivity;
import com.xhy.xhyapp.myactivity.MyPurchaseActivity;
import com.xhy.xhyapp.myactivity.PingtaiNoticeActivity;
import com.xhy.xhyapp.myactivity.RefundActivity;
import com.xhy.xhyapp.myactivity.SetActivity;
import com.xhy.xhyapp.myactivity.SuggestionActivity;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    ImageView shezhi,touphone,tiaozhuan;
    TextView touname,shangpinguanli,dingdanguanli,dianpuguanli,tuishou;
    RelativeLayout re1,re2,re3,re4,re5,re6,re7,re8,re9;
    LinearLayout lin;
    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
        shezhi = ((ImageView) view.findViewById(R.id.my_shezhi));shezhi.setOnClickListener(this);
        touphone = ((ImageView) view.findViewById(R.id.my_touphone));
        touname = ((TextView) view.findViewById(R.id.my_touname));
        shangpinguanli = ((TextView) view.findViewById(R.id.my_shangpinguanli));shangpinguanli.setOnClickListener(this);
        dingdanguanli = ((TextView) view.findViewById(R.id.my_daifahuo));dingdanguanli.setOnClickListener(this);
        dianpuguanli = ((TextView) view.findViewById(R.id.my_daishouhuo));dianpuguanli.setOnClickListener(this);
        tuishou = ((TextView) view.findViewById(R.id.my_tuishou));tuishou.setOnClickListener(this);
        lin = ((LinearLayout) view.findViewById(R.id.my_lin_tiaozhuan));lin.setOnClickListener(this);
        re1 = ((RelativeLayout) view.findViewById(R.id.my_re_01));re1.setOnClickListener(this);
        re2 = ((RelativeLayout) view.findViewById(R.id.my_re_02));re2.setOnClickListener(this);
        re3 = ((RelativeLayout) view.findViewById(R.id.my_re_03));re3.setOnClickListener(this);
        re4 = ((RelativeLayout) view.findViewById(R.id.my_re_04));re4.setOnClickListener(this);
        re5 = ((RelativeLayout) view.findViewById(R.id.my_re_05));re5.setOnClickListener(this);
        re6 = ((RelativeLayout) view.findViewById(R.id.my_re_06));re6.setOnClickListener(this);
        re7 = ((RelativeLayout) view.findViewById(R.id.my_re_07));re7.setOnClickListener(this);
        re8 = ((RelativeLayout) view.findViewById(R.id.my_re_08));re8.setOnClickListener(this);
        re9 = ((RelativeLayout) view.findViewById(R.id.my_re_09));re9.setOnClickListener(this);

    }

    private void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httputils=new HttpUtils();
                httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Merchant/GetShop?merchantId=1",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result=responseInfo.result;
                                try {
                                    JSONObject json=new JSONObject(result);
                                    String state=json.getString("state");
                                    String shopName=json.getString("shopName");
                                    String headPic=json.getString("headPic");
                                    touname.setText(shopName);

                                    Picasso.with(getActivity()).load(headPic).into(touphone);
                                } catch (Exception e) {
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_shezhi:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.my_lin_tiaozhuan://跳转
                Bundle bundle4=new Bundle();
                bundle4.putString("name","全部");
                startActivity(new Intent(getActivity(), MyPurchaseActivity.class).putExtras(bundle4));
                break;
            case R.id.my_shangpinguanli://商品管理
                Bundle bundle=new Bundle();
                bundle.putString("name","待支付");
                startActivity(new Intent(getActivity(), MyPurchaseActivity.class).putExtras(bundle));
                break;
            case R.id.my_daishouhuo://订单管理
                Bundle bundle1=new Bundle();
                bundle1.putString("name","待发货");
                startActivity(new Intent(getActivity(),MyPurchaseActivity.class).putExtras(bundle1));
                break;
            case R.id.my_daifahuo://店铺管理
                Bundle bundle2=new Bundle();
                bundle2.putString("name","待收货");
                startActivity(new Intent(getActivity(),MyPurchaseActivity.class).putExtras(bundle2));
                break;
            case R.id.my_tuishou://退货/售后
                Intent intent=new Intent(getActivity(), RefundActivity.class);
                startActivity(intent);
                break;
            case R.id.my_re_01://账户管理
                startActivity(new Intent(getActivity(),AccountManagementActivity.class ));
                break;
            case R.id.my_re_02://我的收藏
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.my_re_03://平台通知
                startActivity(new Intent(getActivity(), PingtaiNoticeActivity.class));
                break;
            case R.id.my_re_04://合同管理
                startActivity(new Intent(getActivity(), HetongActivity.class ));
                break;
            case R.id.my_re_05://服务中心
                startActivity(new Intent(getActivity(), CommonProblemActivity.class));
                break;
            case R.id.my_re_06://生意圈
                //startActivity(new Intent(getActivity(),   ));
                Toast.makeText(getContext(),"生意圈",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_re_07://关于平台
                startActivity(new Intent(getActivity(), AboutPlatformActivity.class));
                break;
            case R.id.my_re_08://意见反馈
                startActivity(new Intent(getActivity(), SuggestionActivity.class));
                break;
            case R.id.my_re_09://客户管理
                Intent it1 = new Intent(getActivity(), KeHuZiLiaoActivity.class);
                it1.putExtra("MerchantId","10085");
                startActivity(it1);
                break;
            default:
                break;
        }
    }
}

