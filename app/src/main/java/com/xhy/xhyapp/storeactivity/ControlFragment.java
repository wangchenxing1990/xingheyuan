package com.xhy.xhyapp.storeactivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xhy.xhyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class ControlFragment extends Fragment {
    private ListView lv_left;
    private ListView lv_right;

    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=View.inflate(getActivity(), R.layout.commodity_control_fragment,null);
        lv_left= (ListView) view.findViewById(R.id.lv_left);
        lv_right= (ListView) view.findViewById(R.id.lv_right);

        initData();
        return view;
    }

    private void initData() {
        titles.add("蛋类");
        titles.add("木基菌");
        titles.add("海鲜类");
        titles.add("淡水鱼类");
        titles.add("进口水果");

        LeftAdapter lsftAdaprter=new LeftAdapter();
        lv_left.setAdapter(lsftAdaprter);

        RightAdapter rightAdapter=new RightAdapter();
        lv_right.setAdapter(rightAdapter);
    }
    class LeftAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int i) {
            return titles.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView=new TextView(getActivity());
            textView.setText(titles.get(i));
            return textView;
        }
    }

    class RightAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int i) {
            return titles.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView=new TextView(getActivity());
            textView.setText(titles.get(i));
            return textView;
        }
    }

}
