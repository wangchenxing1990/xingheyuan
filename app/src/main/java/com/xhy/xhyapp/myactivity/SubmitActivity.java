package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.xhy.xhyapp.R;

public class SubmitActivity extends AppCompatActivity {
    private RelativeLayout rl_back_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        rl_back_submit= (RelativeLayout) findViewById(R.id.rl_back_submit);
        rl_back_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
