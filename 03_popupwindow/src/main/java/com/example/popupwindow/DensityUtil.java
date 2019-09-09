package com.example.popupwindow;

import android.content.Context;

/**
 * Create by Politness Chen on 2019/8/22--17:49
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从dip的单位转成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /**
     * 根据手机的分辨率从px的单位转成dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
