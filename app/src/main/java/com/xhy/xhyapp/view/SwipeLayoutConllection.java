package com.xhy.xhyapp.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by wangx on 2016/7/2.
 */

/**
 * 侧滑删除的控件
 */
public class SwipeLayoutConllection extends FrameLayout {

    private static final String TAG = SwipeLayoutConllection.class.getSimpleName();
    private ViewDragHelper viewDragHelper;
    private ViewGroup mBackLayout;
    private ViewGroup mFrontLayout;
    private int mRange;
    private int mWidth;
    private int mHeight;
    // 默认 关闭状态
    private SwipeStatus status = SwipeStatus.CLOSE;
    private OnSwipeStatuChangeListener onSwipeStatuChangeListener;
    //  3. 回调  处理事件 处理的结果都在回调里
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {


        /**
         * 返回值决定 子view 是否可以拖动
         * @param child   当前手指拖动的子view
         * @param pointerId  多指操作的使用  手指分配的id
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;//child == mFrontLayout;
        }

        /**
         * 一个子view  被捕获的时候调用
         * @param capturedChild   被捕获的view
         * @param activePointerId  多指操作的时候使用
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 返回值决定拖动到什么位置  还没有真正的移动
         * 修正移动的位置
         * @param child
         * @param left  建议达到的位置    = 当前+ 瞬间变化量
         * @param dx 瞬间变化量
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int oldLeft = mFrontLayout.getLeft();// 左边的坐标
            Log.d(TAG, "clampViewPositionHorizontal: 建议达到的位置: " + left
                    + " === " + oldLeft + "+" + dx
            );
            if (child == mFrontLayout) {
                // 拖动的是  mFrontLayout   -mRange  ----->0
                left = fixFrontLeft(left);
            } else if (child == mBackLayout) {
                // 拖动的是  mBackLayout    mWidth  - mRange ----> mWidth
                left = fixBackLeft(left);
            }

            return left;
        }

        /**
         * 修正backLeft
         * @param left
         * @return
         */
        private int fixBackLeft(int left) {
            if (left < (mWidth - mRange)) {
                left = mWidth - mRange;
            } else if (left > mWidth) {
                left = mWidth;
            }
            return left;
        }

        /**
         * 修正frontleft
         * @param left
         * @return
         */
        private int fixFrontLeft(int left) {
            if (left < -mRange) {
                left = -mRange;
            } else if (left > 0) {
                left = 0;
            }
            return left;
        }

        /**
         * 获取控件横向拖动的范围 返回值不影响拖动
         * 1.  计算伴随动画时长   2.  抗干扰
         *  只要返回大于 0 即可
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mRange;
        }

        /**
         * 子view  位置改变的时候调用  1. 添加伴随动画 2. 状态的更新  3. 添加回调
         * @param changedView  拖动  的子view
         * @param left  clampViewPositionHorizontal 的返回值  拖动的位置
         * @param top
         * @param dx  水平方向的瞬间变化量
         * @param dy  垂直方向的瞬间变化量
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged: " + changedView + "::left = " + left
                    + "::dx = " + dx);
            if (changedView == mFrontLayout) {
                //拖动的是  mFrontLayout  , 将变化量 交给  mBackLayout一份
                mBackLayout.offsetLeftAndRight(dx);//  在原来的基础上设置水平的偏移量
            } else if (changedView == mBackLayout) {
                //拖动的是  mBackLayout  , 将变化量 交给  mFrontLayout
                mFrontLayout.offsetLeftAndRight(dx);//  在原来的基础上设置水平的偏移量
            }

            //  处理状态的更新
            dispathEvent();

            // 手动绘制
            invalidate();
        }

        /**
         * 子view   释放的时候调用   手指抬起
         * @param releasedChild  释放的子view
         * @param xvel   s = vt   速度    水平方向的速度  向左 -  向右+   0
         * @param yvel    垂直方向的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.d(TAG, "onViewReleased: releasedChild = " + releasedChild + "::xvel = " + xvel);
            if (mFrontLayout.getLeft() < -mRange * 0.5 && xvel == 0) {
                open();// 打开
            } else if (xvel < 0) {
                // 向左
                open();
            } else {
                close(); // 其他都需要关闭
            }
        }


    };

    public SwipeLayoutConllection(Context context) {
        this(context, null);
    }

    public SwipeLayoutConllection(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayoutConllection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //  1. 初始化ViewDragHelper对象
//ctrl + alt+ F C V
        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    public SwipeStatus getStatus() {
        return status;
    }

    public void setStatus(SwipeStatus status) {
        this.status = status;
    }

    // 2 处理事件的拦截 决定是否将事件转交给  ViewDragHelper 对象
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        // 让  ViewDragHelper  决定  事件是否拦截
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 处理事件    ViewDragHelper 的事件生效
        viewDragHelper.processTouchEvent(event);
        //  返回 true  表示 消费  事件
        // down  //move  up
        return true;
    }

    /**
     * 测量子view   会调用多次
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在每次测量完成后  数值改变的时候调用
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 获取当前控件的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 获取拖动的范围
        mRange = mBackLayout.getMeasuredWidth();
    }


    // xml ---- view

    //  xml  填充为view 对象 填充完的时候调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 健壮性的判断
        if (getChildCount() < 2) {
            throw new IllegalStateException("you must be  hava 2 children at least!");
        }

        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("your children must be instance of ViewGroup!");
        }

        mBackLayout = (ViewGroup) getChildAt(0);
        mFrontLayout = (ViewGroup) getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //    mBackLayout放在后边
        layoutInit(false);
    }

    private void layoutInit(boolean isOpen) {
        Rect mFrontRect = computeFrontRect(isOpen);
        mFrontLayout.layout(mFrontRect.left, mFrontRect.top, mFrontRect.right, mFrontRect.bottom);
        Rect mBackRect = computeBackRect(mFrontRect);// 根据前边的矩形位置 计算   mBackRect 位置
        mBackLayout.layout(mBackRect.left, mBackRect.top, mBackRect.right, mBackRect.bottom); // 放置 控件的位置

        // 子view 放在前边
        bringChildToFront(mFrontLayout);
    }

    /**
     * 后边的控件的位置
     *
     * @param mFrontRect
     * @return
     */
    private Rect computeBackRect(Rect mFrontRect) {
        return new Rect(mFrontRect.right, mFrontRect.top, mFrontRect.right + mRange, mFrontRect.bottom);
    }

    /**
     * 返回 mFrontRect 位置
     *
     * @return
     */
    private Rect computeFrontRect(boolean isopen) {
        int left = 0;
        if (isopen) {
            left = -mRange;
        }
        return new Rect(left, 0, left + mWidth, 0 + mHeight);
    }

    /**
     * 1.  处理状态更新  2. 添加回调
     */
    private void dispathEvent() {
        // 记录上次的状态
        SwipeStatus preStatus = status;
        status = updateStatus();
        if (onSwipeStatuChangeListener != null) {
            onSwipeStatuChangeListener.onSwiping(this);
            if (preStatus != status) {
                // 上一个状态和当前状态不一致

                if (status == SwipeStatus.CLOSE) {
                    // 关闭
                    onSwipeStatuChangeListener.onClose(this);
                } else if (status == SwipeStatus.OPEN) {
                    onSwipeStatuChangeListener.onOpen(this);
                }

                //   上一个状态是  关闭状态
                if (preStatus == SwipeStatus.CLOSE) {
                    // 将要打开
                    onSwipeStatuChangeListener.onStartOpen(this);
                } else if (preStatus == SwipeStatus.OPEN) {
                    // 上一个状态是打开i状态
                    onSwipeStatuChangeListener.onStartClose(this);
                }
            }
        }

    }

    /**
     * 获取最新的状态
     *
     * @return
     */
    private SwipeStatus updateStatus() {
        if (mFrontLayout.getLeft() == -mRange) {
            return SwipeStatus.OPEN;
        } else if (mFrontLayout.getLeft() == 0) {
            return SwipeStatus.CLOSE;
        }
        return SwipeStatus.SWIPING;
    }

    @Override
    public void computeScroll() {   //很多次      50+
        super.computeScroll();

        // 确定是否继续触发动画
        boolean b = viewDragHelper.continueSettling(true);
        if (b) {
            //执行动画
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 打开或关闭
    ///////////////////////////////////////////////////////////////////////////

    public void open(boolean isSmooth) {
        if (isSmooth) {   //平滑的打开   长发公主    scroller
            //   返回决定是否触发动画
            boolean b = viewDragHelper.smoothSlideViewTo(mFrontLayout, -mRange, 0);
            if (b) {
                // 触发动画   执行动画
                ViewCompat.postInvalidateOnAnimation(this);
            }

        } else {
            layoutInit(true); //默认关闭状态
        }
    }

    /**
     * 打开i
     */
    public void open() {
        open(true);//默认平滑打开
    }

    public void close(boolean isSmooth) {
        if (isSmooth) {

            boolean b = viewDragHelper.smoothSlideViewTo(mFrontLayout, 0, 0);
            if (b) {
                //触发动画
                //执行动画
                ViewCompat.postInvalidateOnAnimation(this);
            }

        } else {  //不是平滑的移动
            layoutInit(false); //默认关闭状态
        }
    }

    /**
     * 关闭
     */
    public void close() {
        close(true);//  默认平滑关闭
    }

    public OnSwipeStatuChangeListener getOnSwipeStatuChangeListener() {
        return onSwipeStatuChangeListener;
    }

    public void setOnSwipeStatuChangeListener(OnSwipeStatuChangeListener onSwipeStatuChangeListener) {
        this.onSwipeStatuChangeListener = onSwipeStatuChangeListener;
    }

    // 状态
    enum SwipeStatus {
        OPEN, CLOSE, SWIPING
    }

    public interface OnSwipeStatuChangeListener {

        void onOpen(SwipeLayoutConllection layout);// 打开

        void onClose(SwipeLayoutConllection layout);// 关闭

        void onSwiping(SwipeLayoutConllection layout);// 正在拖动


        void onStartOpen(SwipeLayoutConllection layout);// 将要打开

        void onStartClose(SwipeLayoutConllection layout);// 将要关闭

    }
}
