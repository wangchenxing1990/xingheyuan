package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.xhy.xhyapp.R;

public class ReFundSuccessActivity extends AppCompatActivity {

    private LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_fund_success);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back_refundsuccess));
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                
            }
        });
    }
}
