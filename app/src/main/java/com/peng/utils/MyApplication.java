package com.peng.utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 2017/9/12.
 */
public class MyApplication extends Application {
    //1.上下文
    public static Context mContext;
    //2.主线程的handler
    public static Handler mHandler;
    public Map<String,String> mCacheJsonMap = new HashMap<>();

    //3.程序入口方法
    public void onCreate(){
        super.onCreate();
        //3.1 初始化上下文
        mContext = this;
        //3.2 初始化主线程的一个handler
        mHandler = new Handler();}

    public static Context getmContext() {    //得到上下文对象
        return mContext;
    }

    public static Handler getmHandler() {    //得到主线程handler对象
        return mHandler;
    }
}

