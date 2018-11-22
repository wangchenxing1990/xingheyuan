package com.xhy.xhyapp.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xhy.xhyapp.R;
import com.xhy.xhyapp.myview.MyReturnPopuWindown;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/2.
 */
public class ReturnActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rl_return_because, rl_back_return;
    private GridView gv_image;
    private Button btn_submit_succ;
    private TextView tv_return_money;
    private int i;
    private List<Integer> immages = new ArrayList<>();
    private String[] titles = new String[]{"货物保险问题", "货物重量问题", "平台问题", "物流问题"};

    public boolean onTouchEvent(MotionEvent event) {
// TODO Auto-generated method stub
        if (myReturnPopuWindown != null && myReturnPopuWindown.isShowing()) {
            myReturnPopuWindown.dismiss();
            myReturnPopuWindown = null;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_request_activity);

        rl_back_return = (RelativeLayout) findViewById(R.id.rl_back_return);
        rl_return_because = (RelativeLayout) findViewById(R.id.rl_return_because);
        gv_image = (GridView) findViewById(R.id.gv_image);
        btn_submit_succ = (Button) findViewById(R.id.btn_submit_succ);
        tv_return_money = (TextView) findViewById(R.id.tv_return_money);

        rl_back_return.setOnClickListener(this);
        rl_return_because.setOnClickListener(this);
        btn_submit_succ.setOnClickListener(this);

        initData();

    }

    private void initData() {
        immages.add(R.drawable.pinzheng1);
        immages.add(R.drawable.pingzheng2);
        immages.add(R.drawable.pingzheng3);

        MyGridAdapter adapter = new MyGridAdapter();
        gv_image.setAdapter(adapter);


    }


    private MyReturnPopuWindown myReturnPopuWindown;

    View.OnClickListener itemOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myReturnPopuWindown.dismiss();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back_return:
                onBackPressed();
                break;
            case R.id.rl_return_because:
                myReturnPopuWindown = new MyReturnPopuWindown(ReturnActivity.this, itemOnclick, R.layout.my_popuwindow);
                myReturnPopuWindown.showAtLocation(ReturnActivity.this.findViewById(R.id.retuen_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View mView = myReturnPopuWindown.getView();
                TextView tv_popu_comlete = (TextView) mView.findViewById(R.id.tv_popu_comlete);

                final TextView tv_insure = (TextView) mView.findViewById(R.id.tv_insure);
                final TextView tv_weight = (TextView) mView.findViewById(R.id.tv_weight);
                final TextView tv_platform = (TextView) mView.findViewById(R.id.tv_platform);
                final TextView tv_traffic = (TextView) mView.findViewById(R.id.tv_traffic);


                tv_popu_comlete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_return_money.setText(titles[i]);
                        tv_return_money.setTextColor(0xffc8c8c8);
                        myReturnPopuWindown.dismiss();

                    }
                });


                tv_insure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_insure.setTextColor(0xff1f2a31);
                        tv_weight.setTextColor(0xff636464);
                        tv_platform.setTextColor(0xff636464);
                        tv_traffic.setTextColor(0xff636464);
                        i = 0;
                    }
                });
                tv_weight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_insure.setTextColor(0xff636464);
                        tv_weight.setTextColor(0xff1f2a31);
                        tv_platform.setTextColor(0xff636464);
                        tv_traffic.setTextColor(0xff636464);
                        i = 1;
                    }
                });
                tv_platform.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_insure.setTextColor(0xff636464);
                        tv_weight.setTextColor(0xff636464);
                        tv_platform.setTextColor(0xff1f2a31);
                        tv_traffic.setTextColor(0xff636464);
                        i = 2;
                    }
                });
                tv_traffic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tv_insure.setTextColor(0xff636464);
                        tv_weight.setTextColor(0xff636464);
                        tv_platform.setTextColor(0xff636464);
                        tv_traffic.setTextColor(0xff1f2a31);
                        i = 3;
                    }
                });


                break;
            case R.id.btn_submit_succ:
                //Toast.makeText(getApplication(),"提交成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SubmitActivity.class);
                startActivity(intent);
                break;
        }

    }


    class MyGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return immages.size();
        }

        @Override
        public Object getItem(int i) {
            return immages.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(ReturnActivity.this, R.layout.grid_item, null);
                holder.imageView = (ImageView) view.findViewById(R.id.iv_grid_view);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.imageView.setImageResource(immages.get(i));

            return view;
        }
    }

    class ViewHolder {
        ImageView imageView;
    }
}
