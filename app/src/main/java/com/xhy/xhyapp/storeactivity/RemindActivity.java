package com.xhy.xhyapp.storeactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xhy.xhyapp.R;

import java.util.ArrayList;
import java.util.List;

public class RemindActivity extends AppCompatActivity {
    LinearLayout image_fanhui;
    private ListView listview_remind;
    private List<Integer> images=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        image_fanhui = ((LinearLayout) findViewById(R.id.ll_back));
        listview_remind = ((ListView) findViewById(R.id.listview_remind));
        RemindAdapter remind=new RemindAdapter();
        listview_remind.setAdapter(remind);
        initData();
        image_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
}

    private void initData() {
        images.add(R.drawable.blueberry);
        images.add(R.drawable.cherry);
        images.add(R.drawable.pitaya);
        images.add(R.drawable.wuliu);
    }

    private class RemindAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view==null){
                holder=new ViewHolder();
                view=getLayoutInflater().inflate(R.layout.remind_item, null);
                holder.image_remind= (ImageView) view.findViewById(R.id.imageView7);
                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }

            holder.image_remind.setImageResource(images.get(position));
            return view;
        }
    }
    class ViewHolder{
        ImageView image_remind;
    }
}
