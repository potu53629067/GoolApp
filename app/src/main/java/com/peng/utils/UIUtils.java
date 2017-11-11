package com.peng.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by peng on 2017/9/12.
 */
public class UIUtils {
    public static Context getContext(){        //1.得到上下文
        return MyApplication.getmContext();}

    public static Resources getResources(){    //2.得到Resource对象
        return getContext().getResources();}

    public static String getString(int resId){    //3.得到String.xml中定义的字符串信息
        return getResources().getString(resId); }

    public static String[] getStrings(int resId){    //4.得到String.xml中定义的字符串数组信息
        return getResources().getStringArray(resId);}

    public static int getColor(int resId){    //5.得到Color.xml定义的颜色信息
        return getResources().getColor(resId);}
        //6.获得包名
    public static String getPackageName() {
        return getContext().getPackageName();
    }
    /**
     * dp-->px
     *
     * @param dp
     * @return
     */
    public static int dp2Px(int dp) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + .5f);
        return px;
    }

    /**
     * px-->do
     *
     * @param px
     * @return
     */
    public static int px2Dp(int px) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int dp = (int) (px / density + .5f);
        return dp;
    }

    /**
     * sp-->px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
