package com.xhy.xhyapp.storeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myfragment.ApplyAmplificationAFragment;
import com.xhy.xhyapp.myfragment.ApplyAmplificationTFragment;
import com.xhy.xhyapp.myfragment.ApplyAmplificationThFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ApplyAmplificationActivity extends AppCompatActivity {

private List<Fragment> fragments=new ArrayList<>();
    private  int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyamplification);
        //初始化fragment
        initFragment();

        Intent intent = getIntent();

        location = intent.getIntExtra("location",9);

        switch (location) {
            case 1:
                showFragment(fragments.get(0));
                break;

            case 2:
                showFragment(fragments.get(1));
                break;

            case 3:
                showFragment(fragments.get(2));
                break;
        }
    }

    private void initFragment() {
        fragments.add(new ApplyAmplificationAFragment());
        fragments.add(new ApplyAmplificationTFragment());
        fragments.add(new ApplyAmplificationThFragment());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_applyamplifiation,fragment).commit();
    }


}
