package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SuggestionActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_suggestion_back;
    private TextView tv_platform, tv_goods, tv_service, tv_other;
    private EditText et_input_suggest;
    private TextView tv_submit_suggest;
    String edit_suggestion;
    int feedBackType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();

    }

    private void initView() {
        rl_suggestion_back = ((RelativeLayout) findViewById(R.id.rl_suggestion_back));
        tv_platform = ((TextView) findViewById(R.id.tv_platform));
        tv_goods = ((TextView) findViewById(R.id.tv_goods));
        tv_service = ((TextView) findViewById(R.id.tv_service));
        tv_other = ((TextView) findViewById(R.id.tv_other));
        et_input_suggest = (EditText) findViewById(R.id.et_input_suggest);
        tv_submit_suggest = (TextView) findViewById(R.id.tv_submit_suggest);

        rl_suggestion_back.setOnClickListener(this);
        tv_platform.setOnClickListener(this);
        tv_goods.setOnClickListener(this);
        tv_service.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_submit_suggest.setOnClickListener(this);

        tv_platform.setTextColor(0xff3fc199);
        tv_platform.setBackgroundResource(R.drawable.lvbiankuang);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_suggestion_back:
                finish();
                break;
            case R.id.tv_platform:

                tv_platform.setTextColor(0xff3fc199);
                tv_goods.setTextColor(0xff1e2439);
                tv_service.setTextColor(0xff1e2439);
                tv_other.setTextColor(0xff1e2439);

                tv_platform.setBackgroundResource(R.drawable.lvbiankuang);
                tv_goods.setBackgroundResource(R.drawable.huibiankuang);
                tv_service.setBackgroundResource(R.drawable.huibiankuang);
                tv_other.setBackgroundResource(R.drawable.huibiankuang);

                feedBackType=0;
                break;
            case R.id.tv_goods:
                tv_platform.setTextColor(0xff1e2439);
                tv_goods.setTextColor(0xff3fc199);
                tv_service.setTextColor(0xff1e2439);
                tv_other.setTextColor(0xff1e2439);

                tv_platform.setBackgroundResource(R.drawable.huibiankuang);
                tv_goods.setBackgroundResource(R.drawable.lvbiankuang);
                tv_service.setBackgroundResource(R.drawable.huibiankuang);
                tv_other.setBackgroundResource(R.drawable.huibiankuang);
                feedBackType=1;
                break;
            case R.id.tv_service:
                tv_platform.setTextColor(0xff1e2439);
                tv_goods.setTextColor(0xff1e2439);
                tv_service.setTextColor(0xff3fc199);
                tv_other.setTextColor(0xff1e2439);

                tv_platform.setBackgroundResource(R.drawable.huibiankuang);
                tv_goods.setBackgroundResource(R.drawable.huibiankuang);
                tv_service.setBackgroundResource(R.drawable.lvbiankuang);
                tv_other.setBackgroundResource(R.drawable.huibiankuang);
                feedBackType=2;
                break;
            case R.id.tv_other:
                tv_platform.setTextColor(0xff1e2439);
                tv_goods.setTextColor(0xff1e2439);
                tv_service.setTextColor(0xff1e2439);
                tv_other.setTextColor(0xff3fc199);

                tv_platform.setBackgroundResource(R.drawable.huibiankuang);
                tv_goods.setBackgroundResource(R.drawable.huibiankuang);
                tv_service.setBackgroundResource(R.drawable.huibiankuang);
                tv_other.setBackgroundResource(R.drawable.lvbiankuang);
                feedBackType=3;
                break;
            case R.id.tv_submit_suggest:
                edit_suggestion = et_input_suggest.getText().toString();
                if (TextUtils.isEmpty(edit_suggestion)){
                    Toast.makeText(SuggestionActivity.this,"意见不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpUtils httpUtils = new HttpUtils();
                            httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Other/FeedBack?merchantId=5&msg="+edit_suggestion+"&feedBackType="+feedBackType,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            String result1 = responseInfo.result;
                                            try {
                                                JSONObject jsonObject=new JSONObject(result1);
                                                String state=jsonObject.getString("state");
                                                switch (state){
                                                    case "0":
                                                        Toast.makeText(SuggestionActivity.this,"操作成功！",Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            System.out.println("----------上传失败");
                                        }
                                    });
                        }
                    }).start();
                }
                break;
        }

    }
}
