package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.PingtaiAdapter;

public class PingtaiNoticeActivity extends AppCompatActivity {

    private ListView listview_pingtai;
    String[] str={"商品活动","消息通知","商品活动","消息通知","商品活动","消息通知"};
    private LinearLayout ll_back_pingtai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingtai_notice);
        listview_pingtai = ((ListView) findViewById(R.id.listview_pingtai));
        PingtaiAdapter pingtaiAdapter=new PingtaiAdapter(PingtaiNoticeActivity.this,str);
        listview_pingtai.setAdapter(pingtaiAdapter);
        ll_back_pingtai = ((LinearLayout) findViewById(R.id.ll_back_pingtai));
        ll_back_pingtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
