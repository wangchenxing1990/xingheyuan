package com.xhy.xhyapp.purchaseactivity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyGridAdapter;
import com.xhy.xhyapp.bean.ProductDtailBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/16.
 */
public class IconTextDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ProductDtailBean> user;
    private LinearLayout ll_return_back,ll_icon_detail,ll_product_parameter,linear_grid_view;
    private TextView tv_product_parameter,tv_icon_detail;
    private View view1,view2;
    private WebView imagessss;
    private GridView grid_view123;
    private ScrollView scroll_imageview;
    private String[] dataUrls;
    private FrameLayout ll_display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_text_detail);
        Bundle bundle = getIntent().getExtras();
        user = (ArrayList) bundle.get("user");
        dataUrls = (String[]) bundle.get("dataUrls");


        initView();
    }

    private void initView() {
        ll_return_back= (LinearLayout) findViewById(R.id.ll_return_back);
        ll_icon_detail= (LinearLayout) findViewById(R.id.ll_icon_detail);
        ll_product_parameter= (LinearLayout) findViewById(R.id.ll_product_parameter);

        tv_icon_detail= (TextView) findViewById(R.id.tv_icon_detail);
        tv_product_parameter= (TextView) findViewById(R.id.tv_product_parameter);

        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);

        scroll_imageview= ((ScrollView) findViewById(R.id.scroll_imageview));
        linear_grid_view= ((LinearLayout) findViewById(R.id.linear_grid_view));

        View view = View.inflate(IconTextDetailActivity.this, R.layout.linearlayout_image, null);
        imagessss = (WebView) view.findViewById(R.id.imagessss);
        imagessss.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ll_display = (FrameLayout) findViewById(R.id.ll_display);
        ll_display.addView(view);

       // imagesss= (ImageView) findViewById(R.id.imagesss);
        grid_view123= (GridView) findViewById(R.id.grid_view123);

        scroll_imageview.setVisibility(View.VISIBLE);

        ll_return_back.setOnClickListener(this);
        ll_icon_detail.setOnClickListener(this);
        ll_product_parameter.setOnClickListener(this);

        imagessss.loadUrl(dataUrls[0]);
//        BitmapUtils bitmapUtils = new BitmapUtils(IconTextDetailActivity.this);
//        bitmapUtils.display(imagessss, dataUrls[0]);
    //    Picasso.with(this).load(dataUrls[0]).fit().centerCrop().into(imagesss);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_return_back:

                finish();

                break;
            case R.id.ll_icon_detail:

                tv_icon_detail.setTextColor(0xff3fc199);
                view1.setBackgroundColor(0xff3fc199);
                tv_product_parameter.setTextColor(0xffcccccc);
                view2.setBackgroundColor(0x00cccccc);

                scroll_imageview.setVisibility(View.VISIBLE);
                linear_grid_view.setVisibility(View.GONE);

               imagessss.loadUrl(dataUrls[0]);
//                BitmapUtils bitmapUtils = new BitmapUtils(IconTextDetailActivity.this);
//                bitmapUtils.display(imagessss, dataUrls[0]);
            //    Picasso.with(this).load(dataUrls[0]).fit().centerCrop().into(imagesss);
                break;
            case R.id.ll_product_parameter:

                tv_product_parameter.setTextColor(0xff3fc199);
                view2.setBackgroundColor(0xff3fc199);
                tv_icon_detail.setTextColor(0xffcccccc);
                view1.setBackgroundColor(0x00cccccc);

                linear_grid_view.setVisibility(View.VISIBLE);
                scroll_imageview.setVisibility(View.GONE);

                MyGridAdapter myGridAdapter=new MyGridAdapter(IconTextDetailActivity.this,user);
                grid_view123.setAdapter(myGridAdapter);

                break;
        }
    }
}
