package com.xhy.xhyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyGridViewRight extends GridView {
    public MyGridViewRight(Context context) {
        super(context,null);
    }

    public MyGridViewRight(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyGridViewRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
