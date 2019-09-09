package com.example.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Create by Politness Chen on 2019/8/22--18:53
 */
public class IndexView extends View{

    //每条的宽和高
    private int itemWidth;
    private int itemHeigh;

    private Paint paint;

    private String[] words = {"A","B","C","D","E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true); //抗锯齿
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置粗体字
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeigh = getMeasuredHeight()/words.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {

            if (touchIndex == i) {
                //设置灰色
                paint.setColor(Color.GRAY);
            } else {
                //设置白色
                paint.setColor(Color.WHITE);
            }


            String word = words[i];

            Rect rect = new Rect();
            //0,1是取一个字母
            paint.getTextBounds(word,0,1,rect);
            paint.setTextSize(35);
            //字母的宽和高
            int wordWidth = rect.width();
            int wordHeight = rect.height();

            //计算每个字母在视图上的坐标位置
            float wordX = itemWidth/2 - wordWidth/2;
            float wordY = itemHeigh/2 + wordHeight/2 + i*itemHeigh;

            canvas.drawText(word,wordX,wordY,paint);
        }
    }

    //字母的下标位置
    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y/itemHeigh);
                if (index != touchIndex) {
                    touchIndex = index;
                    invalidate();  //强制绘制会导致onDraw()执行

                    if (onIndexChangeListener != null && touchIndex < words.length) {
                        onIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }


    //字母变化的接口
    public interface OnIndexChangeListener {
        void onIndexChange(String word);
    }

    private OnIndexChangeListener onIndexChangeListener;

    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        this.onIndexChangeListener = onIndexChangeListener;
    }
}
