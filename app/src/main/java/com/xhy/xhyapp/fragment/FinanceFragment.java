package com.xhy.xhyapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xhy.xhyapp.MainActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myactivity.ApplyRecordActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceFragment extends Fragment {

    public TextView shenqingjilu;

    public FinanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_finance, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shenqingjilu=(TextView) view.findViewById(R.id.shenqingjilu);

        shenqingjilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ApplyRecordActivity.class);
                startActivity(intent);
            }
        });

    }

}
