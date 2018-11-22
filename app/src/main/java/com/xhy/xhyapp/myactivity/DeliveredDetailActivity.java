package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xhy.xhyapp.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class DeliveredDetailActivity extends AppCompatActivity implements View.OnClickListener{

    public ImageView iv_shop_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todelivered_detail);
        init();
    }

    public void init(){
        iv_shop_back=(ImageView) findViewById(R.id.iv_shop_back);
        iv_shop_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_shop_back:
                finish();
            break;
        }
    }
}