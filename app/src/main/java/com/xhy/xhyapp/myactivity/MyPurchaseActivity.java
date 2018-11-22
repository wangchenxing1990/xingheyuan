package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myfragment.CompleteFragment;
import com.xhy.xhyapp.myfragment.InboundFragment;
import com.xhy.xhyapp.myfragment.ToDeliveredFragment;
import com.xhy.xhyapp.myfragment.TobePayFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPurchaseActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private TextView all,dpay,dreceive,dtake;
    private TextView x1,x2,x3,x4;
    private LinearLayout back;
    private String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untreated);
        inData();
        //初始化fragment
        initFragment();

        Intent intent=getIntent();
        num=intent.getStringExtra("name");
        switch (num) {
            case "2":
                showFragment(fragments.get(2));
                x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xffe6e6e6);
                x3.setBackgroundColor(0xff3fc199);x4.setBackgroundColor(0xffe6e6e6);
                //dreceive.setTextColor(0xff3fc199);
                break;
        }

        showFragment(fragments.get(0));


//我的主页上的待支付，待发货，待收货的跳转。
        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        // Toast.makeText(MyPurchaseActivity.this,name,Toast.LENGTH_LONG).show();
        if (name.equals(dpay.getText())) {
            x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xff3fc199);
            x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xffe6e6e6);
            showFragment(fragments.get(1));
        } else if(name.equals(dreceive.getText())){
            x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xffe6e6e6);
            x3.setBackgroundColor(0xff3fc199);x4.setBackgroundColor(0xffe6e6e6);
            showFragment(fragments.get(3));
        }else if (name.equals(dtake.getText())){
            showFragment(fragments.get(2));
            x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xffe6e6e6);
            x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xff3fc199);
        }else if(name.equals(all.getText())){
            showFragment(fragments.get(0));
            x1.setBackgroundColor(0xff3fc199);x2.setBackgroundColor(0xffe6e6e6);
            x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xffe6e6e6);
        }



    }
    private void changeFragment(int i) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragments.get(i)).commit();
    }
    private void inData() {
        back = ((LinearLayout) findViewById(R.id.ll_back001)); back.setOnClickListener(this);
        all = ((TextView) findViewById(R.id.my_caigou_all));all.setOnClickListener(this);
        x1 = ((TextView) findViewById(R.id.my_zcaigou_x1));
        x1.setBackgroundColor(0xff3fc199);
        dpay = ((TextView) findViewById(R.id.my_caigou_dpay));dpay.setOnClickListener(this);
        x2 = ((TextView) findViewById(R.id.my_zcaigou_x2));
        dreceive = ((TextView) findViewById(R.id.my_caigou_dreceive));dreceive.setOnClickListener(this);
        x3 = ((TextView) findViewById(R.id.my_zcaigou_x3));
        dtake = ((TextView) findViewById(R.id.my_caigou_dtake));dtake.setOnClickListener(this);
        x4 = ((TextView) findViewById(R.id.my_zcaigou_x4));
    }

    private void initFragment() {
        fragments.add(new CompleteFragment(MyPurchaseActivity.this));
        fragments.add(new TobePayFragment());
        fragments.add(new ToDeliveredFragment());
        fragments.add(new InboundFragment());
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.my_caigao_frameLayout,fragment).commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_caigou_all:
                showFragment(fragments.get(0));
                x1.setBackgroundColor(0xff3fc199);x2.setBackgroundColor(0xffe6e6e6);
                x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xffe6e6e6);
                break;
            case R.id.my_caigou_dpay:
                showFragment(fragments.get(1));
                x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xff3fc199);
                x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xffe6e6e6);
                break;
            case R.id.my_caigou_dreceive:
                showFragment(fragments.get(3));
                x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xffe6e6e6);
                x3.setBackgroundColor(0xff3fc199);x4.setBackgroundColor(0xffe6e6e6);
                break;
            case R.id.my_caigou_dtake:
                showFragment(fragments.get(2));
                x1.setBackgroundColor(0xffe6e6e6);x2.setBackgroundColor(0xffe6e6e6);
                x3.setBackgroundColor(0xffe6e6e6);x4.setBackgroundColor(0xff3fc199);
                break;
            case R.id.ll_back001:
                finish();
                break;
            default:
                break;
        }
    }
}
