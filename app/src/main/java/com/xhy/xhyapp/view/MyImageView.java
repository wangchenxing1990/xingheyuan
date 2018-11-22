package com.xhy.xhyapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/5/4.
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height = Math.round(width*0.52);
        setMeasuredDimension((int)width,(int)height);
    }
}
