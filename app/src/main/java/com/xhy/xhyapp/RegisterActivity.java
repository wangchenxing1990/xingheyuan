package com.xhy.xhyapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xhy.xhyapp.fragment.CompanyFragment;
import com.xhy.xhyapp.fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();
        ((Button) findViewById(R.id.rg_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        fragments = new ArrayList<>();
        CompanyFragment companyFragment = new CompanyFragment();//自己写fragment的布局
        PersonalFragment personalFragment = new PersonalFragment();//自己写fragment的布局
        fragments.add(companyFragment);
        fragments.add(personalFragment);
    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(this);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);//默认选中第一个页面
    }

    //radioGroup选项改变监听
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int index = radioGroup.indexOfChild(radioGroup.findViewById(i));
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}


