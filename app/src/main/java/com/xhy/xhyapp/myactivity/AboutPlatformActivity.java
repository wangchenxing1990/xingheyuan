package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.xhy.xhyapp.R;

public class AboutPlatformActivity extends AppCompatActivity {

    private RelativeLayout rl_about_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_platform);
        rl_about_back = ((RelativeLayout) findViewById(R.id.rl_about_back));
        rl_about_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
