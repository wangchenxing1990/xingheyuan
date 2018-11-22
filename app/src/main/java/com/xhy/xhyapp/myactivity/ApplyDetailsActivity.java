package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ApplyDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ApplyDetailsActivity extends AppCompatActivity {

    public String string;
    public TextView lixiedu,huankuananniu,huanxianniu;
    public LinearLayout huankuananniubuju,huanxianniubuju;
    public ListView listView;
    public EditText weihuanbenjinjine;
    public List<RepaymentHistory> list=new ArrayList();
    public RepaymentHistory repaymentHistory;

    public  String s;

    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applydetails);

        string = getIntent().getExtras().getString("merchantLoanid");
        init();
        Toast.makeText(ApplyDetailsActivity.this, "http://139.196.234.104:8000/appapi/Merchant/GetMerchantRepayment?merchantId=1&merchantLoanid=" + string,Toast.LENGTH_SHORT).show();
        imageView=(ImageView) findViewById(R.id.call_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


public void init() {

    lixiedu=(TextView) findViewById(R.id.lixiedu);
    huankuananniu=(TextView) findViewById(R.id.huankuananniu);
    huanxianniu=(TextView) findViewById(R.id.huanxianniu);
    huankuananniubuju=(LinearLayout) findViewById(R.id.huankuananniubuju);
    huanxianniubuju=(LinearLayout) findViewById(R.id.huanxianniubuju);
    weihuanbenjinjine=(EditText) findViewById(R.id.weihuanbenjinjine);
    listView=(ListView) findViewById(R.id.benjinhuankuanjilu);




    String ApiUrl = "http://139.196.234.104:8000/appapi/Merchant/GetMerchantRepayment?merchantId=1&merchantLoanid=" + string;



    HttpUtils http = new HttpUtils();
    http.send(HttpRequest.HttpMethod.POST, ApiUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);

                    lixiedu.setText("预计到期利息"+jsonObject.getString("noReturnInterestl")+"元");
                    if ("0".equals(jsonObject.getString("days"))){
                        huanxianniubuju.setBackgroundResource(R.drawable.kehuankuan);
                    }else {
                        huanxianniu.setText("距还息日"+jsonObject.getString("days")+"天");
                    }
                    weihuanbenjinjine.setHint(jsonObject.getString("noReturnPrincipal"));

                     s=jsonObject.getString("noReturnPrincipal");

                    huankuananniubuju.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Double d1=Double.parseDouble(weihuanbenjinjine.getText().toString());
                            Double d2= Double.parseDouble(s);

                    if((d1.compareTo(d2)>0)){

                        Toast.makeText(ApplyDetailsActivity.this,"还款金额超出应还本金",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });




                    JSONArray jsonArray = jsonObject.getJSONArray("loanList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        repaymentHistory = new RepaymentHistory();
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        // System.out.print("========"+jsonObject1+"+++++++++");

                       repaymentHistory.setHuankuancishu(jsonObject1.getString("rowIndex"));
                       repaymentHistory.setHuankuanjine(jsonObject1.getString("repaymentMoney"));
                        repaymentHistory.setHuankuanriqi(jsonObject1.getString("addTime"));
                        list.add(repaymentHistory);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ApplyDetailsAdapter applyDetailsAdapter=new ApplyDetailsAdapter(ApplyDetailsActivity.this,list);
                listView.setAdapter(applyDetailsAdapter);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("--------------------错误");
                Toast.makeText(ApplyDetailsActivity.this,"错误",Toast.LENGTH_SHORT).show();
            }


        });
    }


}
