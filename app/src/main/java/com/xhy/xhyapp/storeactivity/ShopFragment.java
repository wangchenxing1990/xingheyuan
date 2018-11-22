package com.xhy.xhyapp.storeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.SwipeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements View.OnClickListener {
    private View inflate;
    private RelativeLayout re_first;
    private RelativeLayout re_second;
    private RelativeLayout third_re;

    SwipeAdapter swipeAdapter;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_shop, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        re_first = ((RelativeLayout) inflate.findViewById(R.id.re_first));
        re_second = ((RelativeLayout) inflate.findViewById(R.id.second_re));
        third_re = ((RelativeLayout) inflate.findViewById(R.id.thirdd_re));

        re_first.setOnClickListener(this);
        re_second.setOnClickListener(this);
        third_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_first:
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.second_re:
                Intent intent1 = new Intent(getActivity(), CustomActivity.class);
                startActivity(intent1);
                break;
            case R.id.thirdd_re:
                Intent intent2 = new Intent(getActivity(), RemindActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
