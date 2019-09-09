package com.example.myviewpager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Create by Politness Chen on 2019/9/5--16:33
 */
public class MyViewPager extends ViewGroup{

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要的方法给重写
     * 3.在onTouchEvent()把事件传递给手势识别器
     * @param context
     * @param attrs
     */
    private GestureDetector detector;
    private int currentIndex;

    private Scroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    private void initview(final Context context) {
        scroller = new Scroller(context);
        detector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(context,"长按",Toast.LENGTH_SHORT).show();
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                scrollBy((int)distanceX,0);

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context,"双击",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在每个屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            child.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    private float startX;

    private float downX;
    private float downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean reslut = false;  //默认传给孩子
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = Math.abs(endX - downX);
                float distanceY = Math.abs(endY - downY);
                if (distanceX > distanceY && distanceX > 5) {
                    reslut = true;
                } else {
                    scrollToPager(currentIndex);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return reslut;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                //偏移量
                int tempIndex = currentIndex;
                if (startX - endX > getWidth()/2) {
                    tempIndex ++;
                } else if (endX - startX > getWidth()/2) {
                    tempIndex --;
                }
                scrollToPager(tempIndex);
                break;
        }

        return true;
    }

    //屏蔽非法值，根据位置移动到指定界面
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount()-1) {
            tempIndex = getChildCount()-1;
        }

        currentIndex = tempIndex;

        if (mOnPagerChangeListener != null) {
            mOnPagerChangeListener.onScrollToPager(currentIndex);
        }

        int distanceX = currentIndex*getWidth() - getScrollX();
        //移动到指定的位置
        //scrollTo(currentIndex*getWidth(),0);
        scroller.startScroll(getScrollX(),0,distanceX,0,Math.abs(distanceX));
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            float cuurX = scroller.getCurrX();
            scrollTo((int) cuurX,0);
            postInvalidate();
        }
    }

    public interface OnPagerChangeListener{
        void onScrollToPager(int position);
    }

    private OnPagerChangeListener mOnPagerChangeListener;

    public void setOnPagerChangeListener(OnPagerChangeListener l){
        mOnPagerChangeListener = l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }
}
