package com.example.slidemenu;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Create by Politness Chen on 2019/8/23--18:36
 */
public class SlideLayout extends FrameLayout{

    private View contenView;
    private View menuView;

    private int contentWidth;
    private int menuWidth;
    private int viewHeight;

    private Scroller scroller;

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 当布局文件加载完成的时候回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contenView = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contenView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = contenView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //指定菜单的位置
        menuView.layout(contentWidth,0,contentWidth+menuWidth,viewHeight);
    }

    private float startX;
    private float startY;
    private float downX;  //只赋值一次
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = endX - startX;  //平移的距离

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());

                startX = event.getX();
                startY = event.getY();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX - downX);
                float DY = Math.abs(endY - downY);

                if (DX > DY && DX > 8) {  //DX大于8个像素
                    //水平方向滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX(); //偏移量
                if (totalScrollX < menuWidth/2) {
                    //关闭menu
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intecept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = startX = event.getX();
                if (onStateChangeListener != null) {
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = endX - startX;  //平移的距离

                startX = event.getX();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX - downX);
                if (DX > 8) {  //DX大于8个像素
                    //水平方向滑动
                    intecept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return intecept;
    }

    public void openMenu() {
        int distanceX = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onOpen(this);
        }
    }

    public void closeMenu() {
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    public interface OnStateChangeListener {
        void onClose(SlideLayout slideLayout);
        void onDown(SlideLayout slideLayout);
        void onOpen(SlideLayout slideLayout);
    }

    private OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
