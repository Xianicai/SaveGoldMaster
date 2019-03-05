package com.savegoldmaster.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class Mobile {

    /** 屏幕宽度 */
    public static int SCREEN_WIDTH;

    /** 屏幕高度 */
    public static int SCREEN_HEIGHT;

    /** 16:9的高度 */
    public static int HEIGHT_16_9;

    /** 4:3的高度 */
    public static int HEIGHT_4_3;

    /** 屏幕density */
    public static float DENSITY;

    /** 屏幕density */
    public static float SCALED_DENSITY;

    /** 手机状态栏高度 */
    public static int STATUS_BAR_HEIGHT;

    public static void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 高比宽要长，说明是竖屏
        if (metrics.heightPixels >= metrics.widthPixels) {
            SCREEN_WIDTH = metrics.widthPixels;
            SCREEN_HEIGHT = metrics.heightPixels;
        }
        // 宽比高要长，可能是横屏的参数
        else {
            SCREEN_WIDTH = metrics.heightPixels;
            SCREEN_HEIGHT = metrics.widthPixels;
        }
        HEIGHT_16_9 = SCREEN_WIDTH * 9 / 16;
        HEIGHT_4_3 = SCREEN_WIDTH * 3 / 4;
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
        metrics = null;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
