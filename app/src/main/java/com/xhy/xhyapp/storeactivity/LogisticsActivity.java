package com.xhy.xhyapp.storeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xhy.xhyapp.R;

public class LogisticsActivity extends AppCompatActivity {

    private ListView list_logist;
    // ArrayAdapter<String> arr_adapter;
    String[] str = {"【河南省】郑州市东风南路派件员刘大伟正在为您派件", "【河南省】郑州市东风南路派件员刘大伟正在为您派件",
            "【河南省】郑州市东风南路派件员刘大伟正在为您派件", "【河南省】郑州市东风南路派件员刘大伟正在为您派件", "【河南省】郑州市东风南路派件员刘大伟正在为您派件"};
    String[] time = {"2016-07-26    10:43:25", "2016-07-26    10:43:25",
            "2016-07-26    10:43:25", "2016-07-26    10:43:25", "2016-07-26    10:43:25"};
    private LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list_logist = ((ListView) findViewById(R.id.list_logistics));

//        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);
//        list_logist.setAdapter(arr_adapter);
        LogisticsAdapter logisticsAdapter = new LogisticsAdapter();
        list_logist.setAdapter(logisticsAdapter);
    }

    private class LogisticsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public Object getItem(int position) {
            return str[position];
        }

        @Override
        public long getItemId(int positon) {
            return positon;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(LogisticsActivity.this, R.layout.logistics_item, null);

                holder.txt_logis = (TextView) view.findViewById(R.id.txt_logist);
                holder.txt_shuzi = (TextView) view.findViewById(R.id.txt_shuzi);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (position == 0) {
                holder.txt_logis.setTextColor(0xff3fc199);
                holder.txt_shuzi.setTextColor(0xff3fc199);
            }
            holder.txt_logis.setText(str[position]);
            holder.txt_shuzi.setText(time[position]);
            return view;
        }
    }

    class ViewHolder {
        TextView txt_logis, txt_shuzi;
    }
}
