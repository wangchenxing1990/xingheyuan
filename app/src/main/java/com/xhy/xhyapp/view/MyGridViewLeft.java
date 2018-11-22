package com.xhy.xhyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/8/19.
 */
public class MyGridViewLeft extends GridView {
    public MyGridViewLeft(Context context) {
        super(context,null);
    }

    public MyGridViewLeft(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyGridViewLeft(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
