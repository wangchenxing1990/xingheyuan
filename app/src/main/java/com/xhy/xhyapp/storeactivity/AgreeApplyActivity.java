package com.xhy.xhyapp.storeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.xhy.xhyapp.R;

public class AgreeApplyActivity extends AppCompatActivity {

    private LinearLayout image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_apply);
        image_back = ((LinearLayout) findViewById(R.id.ll_back));
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
