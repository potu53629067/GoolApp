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

    public static String getPackageName() {
        return getContext().getPackageName();
    }
}
