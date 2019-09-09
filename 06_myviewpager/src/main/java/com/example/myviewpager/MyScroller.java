package com.example.myviewpager;

import android.os.SystemClock;

/**
 * Create by Politness Chen on 2019/9/5--18:54
 * desc:
 */
public class MyScroller {

    private float startX;
    private float startY;

    private int distanceX;
    private int distanceY;
    private long startTime;  //开始的时间
    private boolean isFinish;  //是否移动完成

    private long totalTime = 500L;
    private float cuurX;

    public float getCuurX() {
        return cuurX;
    }

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis(); //系统时间;
        this.isFinish = false;
    }

    public boolean computeScrollOffset() {
        if (isFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        //这一小段花费的时间
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            //还没有移动结束
            //计算平均速度
            float distanceSmallX = passTime*distanceX/totalTime;
            cuurX = startX + distanceSmallX;
        } else {
            //移动结束
            isFinish = true;
            cuurX = startX + distanceX;
        }

        return true;
    }
}
