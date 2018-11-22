package com.xhy.xhyapp.myactivity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xhy.xhyapp.R;


public class OffLineActivity extends AppCompatActivity {

    private RelativeLayout rl_off_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.off_line_activity);
        rl_off_back= (RelativeLayout) findViewById(R.id.rl_off_back);
        rl_off_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
