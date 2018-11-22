package com.xhy.xhyapp.storeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhy.xhyapp.MainActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.fragment.Fragment_shangpin_three;
import com.xhy.xhyapp.fragment.Fragment_shangpin_two;
import com.xhy.xhyapp.fragment.Fragmetn_shangpin_one;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/26.
 */
public class CommodityControlActivity extends FragmentActivity implements View.OnClickListener,Fragmetn_shangpin_one.CallBackValue{

    private ImageView iv_shop_back;
    private ImageView tv_order_control_num;
    private TextView tv_order_control;
    private TextView tv_com_control;
    private TextView tv_shop_control;
    private String num;
    private List<Fragment> fragments=new ArrayList<>();
    private LinearLayout ll_back;
    TextView quanxuan ;
    boolean quanxuanclick ;
    int shangpingguanliId;
    Handler handler;
    private String pathitem,itemcatch,caId;

    public Handler getHandler() {
        return handler;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_control);
        quanxuan = ((TextView) findViewById(R.id.iv_shop_quanxuan));//初始化全选按钮
        quanxuan.setTextColor(0xff4EC6A1);
        shangpingguanliId = 1;



        initView();
        initFragment();
        Intent intent=getIntent();
        num=intent.getStringExtra("name");
        switch (num){
            case "0":
                shangpinguanli();
                tv_com_control.setTextColor(0xff3fc199);
                break;
            case "1":
                changeFragment(1);
                tv_order_control.setTextColor(0xff3fc199);
                break;
            case "2":
                changeFragment(2);
                tv_shop_control.setTextColor(0xff3fc199);
                break;
        }
    }



    private void initFragment() {
        fragments.add(new ControlFragment());
        fragments.add(new OrderFragment());
        fragments.add(new ShopFragment());
    }

    private void initView() {
        iv_shop_back= (ImageView) findViewById(R.id.iv_shop_back);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));

        tv_com_control= (TextView) findViewById(R.id.tv_com_control);
        tv_order_control= (TextView) findViewById(R.id.tv_order_control);
        tv_shop_control= (TextView) findViewById(R.id.tv_shop_control);
        tv_order_control_num= (ImageView) findViewById(R.id.tv_order_control_num);

        ll_back.setOnClickListener(this);
        tv_com_control.setOnClickListener(this);
        tv_order_control.setOnClickListener(this);
        tv_shop_control.setOnClickListener(this);
        tv_order_control_num.setOnClickListener(this);
    }


    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.tv_com_control:
                tv_com_control.setTextColor(0xff3fc199);
                tv_order_control.setTextColor(0xff5a5a5a);
                tv_shop_control.setTextColor(0xff5a5a5a);
                shangpinguanli();
                break;
            case R.id.tv_order_control:
                tv_order_control.setTextColor(0xff3fc199);
                tv_com_control.setTextColor(0xff5a5a5a);
                tv_shop_control.setTextColor(0xff5a5a5a);
                changeFragment(1);
                break;
            case R.id.tv_shop_control:
                tv_shop_control.setTextColor(0xff3fc199);
                tv_com_control.setTextColor(0xff5a5a5a);
                tv_order_control.setTextColor(0xff5a5a5a);
                changeFragment(2);
                break;
            case R.id.tv_order_control_num:
                break;
        }
    }

    private Fragment lastFragment;
    /**
     * 切换不同的fragment
     *
     * @param i
     */
    public void changeFragment(int i) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_control, fragments.get(i)).commit();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // 上一个不为空,则隐藏上一个
//        if (lastFragment != null) {
//            transaction.hide(lastFragment);
//        }
//
//        Fragment fragment = fragments.get(i);
//        //fragment不能重复添加 已经存在了就直接显示，不存在就添加
//        if (fragment.isAdded()) {
//            transaction.show(fragment);
//        } else {
//            transaction.add(R.id.frame_control, fragment);
//        }
//        transaction.commit();
//        lastFragment = fragment;
    }
    @Override
    public void SendMessageValue(String arg0, String arg1,String arg2) {
        pathitem = arg0;
        itemcatch = arg1;
        caId = arg2;
    }

    private void shangpinguanli() {
        final Fragmetn_shangpin_one fragmetn_shangpin_one = new Fragmetn_shangpin_one();//one为末图
        final Fragment_shangpin_two fragment_shangpin_two = new Fragment_shangpin_two();
        final Fragment_shangpin_three fragment_shangpin_three = new Fragment_shangpin_three();//three为首图

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_control, fragment_shangpin_three).commit();//提交首页
        quanxuan.setTextColor(0xff4EC6A1);
        quanxuanclick = false;

        fragmetn_shangpin_one.setOnButtonClick(new Fragmetn_shangpin_one.OnButtonClick() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("pathitem",pathitem);
                bundle.putString("itemcatch",itemcatch);
                bundle.putString("caId",caId);
                fragment_shangpin_two.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_control, fragment_shangpin_two).commit();
                shangpingguanliId = 2;
                quanxuan.setTextColor(0xff4EC6A1);
                quanxuanclick = false;
            }
        });
        fragmetn_shangpin_one.setOnHomeClick(new Fragmetn_shangpin_one.OnHomeClick() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommodityControlActivity.this, MainActivity.class));
                finish();
            }
        });
        fragment_shangpin_two.setOnButtonClick(new Fragment_shangpin_two.OnButtonClick() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_control, fragment_shangpin_three).commit();
                shangpingguanliId = 3;
            }
        });
        fragment_shangpin_three.setOnButtonClick(new Fragment_shangpin_three.OnButtonClick() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_control, fragmetn_shangpin_one).commit();
                shangpingguanliId = 1;
                quanxuan.setTextColor(0xffffffff);
                quanxuanclick = true;
            }
        });
        fragment_shangpin_three.setOnHomeClick(new Fragment_shangpin_three.OnHomeClick() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommodityControlActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
