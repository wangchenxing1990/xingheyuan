package com.xhy.xhyapp.storeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xhy.xhyapp.MainActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.MyListSendAdapter;
import com.xhy.xhyapp.adapter.MyListWaitAdapter;
import com.xhy.xhyapp.adapter.MyReturnAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    public OrderFragment() {
    }

    private TextView tv_wait_shipments;
    private TextView tv_had_send;
    private TextView tv_return_deal;

    private ListView lv_wait_shipments;
    private ListView lv_had_send;
    private ListView lv_return_deal;
    private ImageView iv_click_home;

    private List<Integer> images = new ArrayList<>();
    //private FrameLayout fra_order_form;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.order_control_fragment, null);

        tv_wait_shipments = (TextView) view.findViewById(R.id.tv_wait_shipments);
        tv_had_send = (TextView) view.findViewById(R.id.tv_had_send);
        tv_return_deal = (TextView) view.findViewById(R.id.tv_return_deal);
        iv_click_home= (ImageView) view.findViewById(R.id.iv_click_home);

        // fra_order_form= (FrameLayout) view.findViewById(R.id.fra_order_form);
        lv_wait_shipments = (ListView) view.findViewById(R.id.lv_wait_shipments);
        lv_had_send = (ListView) view.findViewById(R.id.lv_had_send);
        lv_return_deal = (ListView) view.findViewById(R.id.lv_return_deal);
        lv_return_deal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ReturnApplyActivity.class);
                startActivity(intent);
            }
        });

        tv_wait_shipments.setOnClickListener(this);
        tv_had_send.setOnClickListener(this);
        tv_return_deal.setOnClickListener(this);
        iv_click_home.setOnClickListener(this);

        initData();
        tv_wait_shipments.setTextColor(0xff3fc199);
        return view;
    }

    private void initData() {
        images.add(R.drawable.blueberry);
        images.add(R.drawable.cherry);
        images.add(R.drawable.honey_peach);
        images.add(R.drawable.blueberry);
        images.add(R.drawable.cherry);
        images.add(R.drawable.honey_peach);

        MyListWaitAdapter waitAdapter = new MyListWaitAdapter(getActivity(), images);
        lv_wait_shipments.setAdapter(waitAdapter);

        MyListSendAdapter sendAdapter = new MyListSendAdapter(getActivity(), images);
        lv_had_send.setAdapter(sendAdapter);

        MyReturnAdapter returnAdapter = new MyReturnAdapter(getActivity(), images);
        lv_return_deal.setAdapter(returnAdapter);
    }

    /**
     * 设置点击监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wait_shipments:
                tv_wait_shipments.setTextColor(0xff3fc199);
                tv_had_send.setTextColor(0xff5a5a5a);
                tv_return_deal.setTextColor(0xff5a5a5a);

                lv_wait_shipments.setVisibility(View.VISIBLE);
                lv_had_send.setVisibility(View.GONE);
                lv_return_deal.setVisibility(View.GONE);

                break;
            case R.id.tv_had_send:
                tv_had_send.setTextColor(0xff3fc199);
                tv_wait_shipments.setTextColor(0xff5a5a5a);
                tv_return_deal.setTextColor(0xff5a5a5a);

                lv_had_send.setVisibility(View.VISIBLE);
                lv_wait_shipments.setVisibility(View.GONE);
                lv_return_deal.setVisibility(View.GONE);

                break;
            case R.id.tv_return_deal:

                tv_return_deal.setTextColor(0xff3fc199);
                tv_wait_shipments.setTextColor(0xff5a5a5a);
                tv_had_send.setTextColor(0xff5a5a5a);

                lv_return_deal.setVisibility(View.VISIBLE);
                lv_had_send.setVisibility(View.GONE);
                lv_wait_shipments.setVisibility(View.GONE);
                break;

            case R.id.iv_click_home:
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
