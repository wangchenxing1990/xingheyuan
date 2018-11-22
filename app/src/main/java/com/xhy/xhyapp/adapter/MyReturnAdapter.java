package com.xhy.xhyapp.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhy.xhyapp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class MyReturnAdapter extends BaseAdapter{
    private final FragmentActivity activity;
    private final List<Integer> images;
    private TextView txt_untreated;

    public MyReturnAdapter(FragmentActivity activity, List<Integer> images) {
        this.activity=activity;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder holder=null;
        if (view==null){
            holder=new Viewholder();
            view=View.inflate(activity, R.layout.wait_deal_item,null);
            holder.imageView4= (ImageView) view.findViewById(R.id.imageView4);
            holder.txt_untreated = ((TextView) view.findViewById(R.id.txt_untreated));
            view.setTag(holder);
        }else{
        holder= (Viewholder) view.getTag();
        }
        holder.imageView4.setImageResource(images.get(i));
//        final Viewholder finalHolder = holder;
//        holder.txt_untreated.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Intent intent=new Intent(activity, ReturnApplyActivity.class);
//                activity.startActivity(intent);
//                return false;
//            }

//            @Override
//            public void onClick(View view) {
//                finalHolder.txt_untreated.setTextColor(0xff3fc199);
//                finalHolder.txt_untreated.setBackgroundResource(R.drawable.lvbiankuang);
//                Intent intent=new Intent(activity, ReturnApplyActivity.class);
//                activity.startActivity(intent);
//            }
      //  });
        return view;
    }

    class Viewholder{
        public ImageView imageView4;
        public TextView txt_untreated;
    }
}
