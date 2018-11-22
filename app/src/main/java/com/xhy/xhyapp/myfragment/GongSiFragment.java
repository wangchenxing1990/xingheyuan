package com.xhy.xhyapp.myfragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GongSiFragment extends Fragment {

    Handler handler2;
    Intent it2;
    String MerchantId;
    String result1;
    TextView namecompany,dizhi,fpersonname,faId,count,fPhone;
    ImageView license,before,after;
    String zhizhao,zphoto,fphoto;

    public GongSiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gong_si, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        handler2 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        try {
                            JSONObject jsonObject = new JSONObject(result1);
                            namecompany.setText(jsonObject.getString("companyName"));
                            dizhi.setText(jsonObject.getString("companyAddress"));
                            fpersonname.setText(jsonObject.getString("realName"));
                            faId.setText(jsonObject.getString("idCardNo"));
                            count.setText(jsonObject.getString("bankAccount"));
                            fPhone.setText(jsonObject.getString("mob"));

                            zhizhao = jsonObject.getString("license");
                            zphoto = jsonObject.getString("idCardBeforePhoto");
                            fphoto = jsonObject.getString("idCardAfterPhoto");

                            BitmapUtils bitmapUtils = new BitmapUtils(getContext());
                            bitmapUtils.display(license,zhizhao);
                            bitmapUtils.display(before,zphoto);
                            bitmapUtils.display(after,fphoto);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        it2 = getActivity().getIntent();
        MerchantId = it2.getStringExtra("MerchantId");
        namecompany = ((TextView) view.findViewById(R.id.my_kh_gs_namecompany));
        dizhi = ((TextView) view.findViewById(R.id.my_kh_gs_dizh));
        fpersonname = ((TextView) view.findViewById(R.id.my_kh_gs_fpersoname));
        faId = ((TextView) view.findViewById(R.id.my_kh_gs_faId));
        count = ((TextView) view.findViewById(R.id.my_kh_gs_count));
        fPhone = ((TextView) view.findViewById(R.id.my_kh_gs_fPhone));
        license = ((ImageView) view.findViewById(R.id.my_kh_gs_zhizhao));
        before = ((ImageView) view.findViewById(R.id.my_kh_gs_photeZ));
        after = ((ImageView) view.findViewById(R.id.my_kh_gs_photeF));

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path2 = "http://139.196.234.104:8000/appapi/Merchant/GetMerchantInfo?MerchantId="+MerchantId;
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, path2, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        result1 = responseInfo.result;
                        Message me1 = new Message();
                        me1.what = 0;
                        handler2.sendMessage(me1);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getContext(),"信息获取失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
