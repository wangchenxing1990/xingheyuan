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
public class GeRenFragment extends Fragment {

    private Intent intent3;
    private Handler handler3;
    TextView personname,liveadress,workspace,personId,personmob;
    String zzz,fff;
    ImageView zphoto,fphoto;
    String MerchantId;
    String result2;

    public GeRenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ge_ren, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler3 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        try {
                            JSONObject jsonObject = new JSONObject(result2);
                            personname.setText(jsonObject.getString("realName"));
                            liveadress.setText(jsonObject.getString("MerchantAddress"));
                            workspace.setText(jsonObject.getString("companyAddress"));
                            personId.setText(jsonObject.getString("idCardNo"));
                            personmob.setText(jsonObject.getString("mob"));

                            zzz = jsonObject.getString("idCardBeforePhoto");
                            fff = jsonObject.getString("idCardAfterPhoto");

                            BitmapUtils bitmapUtils = new BitmapUtils(getContext());
                            bitmapUtils.display(zphoto,zzz);
                            bitmapUtils.display(fphoto,fff);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        intent3 = getActivity().getIntent();
        MerchantId = intent3.getStringExtra("MerchantId");
        personname = ((TextView) view.findViewById(R.id.my_kh_gr_personname));
        liveadress = ((TextView) view.findViewById(R.id.my_kh_gr_liveaddress));
        workspace = ((TextView) view.findViewById(R.id.my_kh_gr_workspace));
        personId = ((TextView) view.findViewById(R.id.my_kh_gr_personId));
        personmob = ((TextView) view.findViewById(R.id.my_kh_gr_personmob));

        zphoto = ((ImageView) view.findViewById(R.id.my_kh_gr_zphote));
        fphoto = ((ImageView) view.findViewById(R.id.my_kh_gr_fphote));


        new Thread(new Runnable() {
            @Override
            public void run() {
                String path3 = "http://139.196.234.104:8000/appapi/Merchant/GetMerchantInfo?MerchantId="+MerchantId;
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, path3, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        result2 = responseInfo.result;
                        Message me2 = new Message();
                        me2.what = 0;
                        handler3.sendMessage(me2);
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
