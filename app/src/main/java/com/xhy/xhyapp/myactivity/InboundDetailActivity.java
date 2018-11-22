package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.storeactivity.LogisticsActivity;

public class InboundDetailActivity extends AppCompatActivity {

    private RelativeLayout rl_qiche;
private LinearLayout ll_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound_detail);
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
        rl_qiche = ((RelativeLayout) findViewById(R.id.rl_qiche));

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_qiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InboundDetailActivity.this, LogisticsActivity.class);
                startActivity(intent);
            }
        });

    }
}
