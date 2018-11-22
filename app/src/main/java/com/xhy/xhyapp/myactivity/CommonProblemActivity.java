package com.xhy.xhyapp.myactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myfragment.CommonProblemFragment;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CommonProblemActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicecenter);

        Fragment fragment=new CommonProblemFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.my_fuwuzhongxin_frameLayout,fragment).commit();

    }
}
