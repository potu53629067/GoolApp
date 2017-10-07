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
    public static Context mContext;
    public static Handler mHandler;
    public Map<String,String> mCacheJsonMap = new HashMap<>();

    public void onCreate(){
        super.onCreate();
        mContext = this;
        mHandler = new Handler();}

    public static Context getmContext() {    //得到上下文对象
        return mContext;
    }

    public static Handler getmHandler() {    //得到主线程handler对象
        return mHandler;
    }
}

