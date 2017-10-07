package com.peng.factory;

import com.peng.proxy.ThreadPoolProxy;

/**
 * Created by peng on 2017/9/16.
 */
public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadProxy;
    static ThreadPoolProxy mDownloadThreadProxy;
    //1.返回一个普通类型的线程池代理
    public static ThreadPoolProxy createNormalThreadProxy(){
        if(mNormalThreadProxy == null){
            synchronized (ThreadPoolProxyFactory.class){
                if(mNormalThreadProxy == null){
                    mNormalThreadProxy = new ThreadPoolProxy(5);
                }
            }
        }
        return mNormalThreadProxy;
    }
    //返回一个下载的线程池代理
    public static ThreadPoolProxy createDownloadThreadProxy(){
        if(mDownloadThreadProxy == null){
            synchronized (ThreadPoolProxy.class){
                if (mDownloadThreadProxy == null){
                    mDownloadThreadProxy = new ThreadPoolProxy(3);
                }
            }
        }
        return mDownloadThreadProxy;
    }
}
