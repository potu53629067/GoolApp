package com.peng.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by peng on 2017/9/16.
 */
public class ThreadPoolProxy {

    //执行器对象
    ThreadPoolExecutor mExecutor;
    private int mCorePoolSize;//核心池大小
    private int mMaximumPoolSize;//最大线程数
    /*public ThreadPoolProxy(int corePoolSize,int maximumPoolSize){
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }*/
    //传一个参数也可以
    public ThreadPoolProxy(int nThread){
        mCorePoolSize = nThread;
        mMaximumPoolSize = nThread;
    }
    //1.执行任务（任务交给线程池执行）
    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }
    //2.提交任务（任务提交给线程池执行）
    public Future<?> submit(Runnable task){
        initThreadPoolExecutor();
        Future<?> submit = mExecutor.submit(task);
        return submit;
    }
    //3.移除任务(从线程池里面移除任务)
    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
    //创建对应的线程池执行器对象
    private void initThreadPoolExecutor() {
        if(mExecutor == null || mExecutor.isShutdown()||mExecutor.isTerminated()){
            long keepAliveTime = 5000;//保持时间
            TimeUnit unit = TimeUnit.MILLISECONDS;//时间单位
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();//任务队列
            ThreadFactory threadFactory = Executors.defaultThreadFactory();//线程工厂
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();//异常捕获器
            mExecutor = new ThreadPoolExecutor(
                    mCorePoolSize,  //核心池大小
                    mMaximumPoolSize,   //最大线程数
                    keepAliveTime,  //保持时间
                    unit,   //时间单位
                    workQueue,  //任务队列
                    threadFactory,  //线程工厂
                    handler //异常捕获器
            );
        }
    }
}
