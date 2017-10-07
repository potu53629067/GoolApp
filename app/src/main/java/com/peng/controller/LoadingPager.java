package com.peng.controller;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.peng.factory.ThreadPoolProxyFactory;
import com.peng.utils.MyApplication;

import appmark.peng.com.myapplication.R;

/**
 * Created by peng on 2017/9/13.
 */
public abstract class LoadingPager extends FrameLayout {
    //视图有4种情况,那么习惯性的定义状态
    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_SUCCESS = 3;
    //第5个常量是代表当前状态,默认为第一个状态:
    public int mCurState = STATE_LOADING;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);
        initCommonView(context);
    }

    private void initCommonView(Context context) {
        //加载中视图
        mLoadingView = View.inflate(context, R.layout.pager_loading,null);
        //空视图
        mEmptyView = View.inflate(context, R.layout.pager_empty,null);
        //错误视图
        mErrorView = View.inflate(context, R.layout.pager_error,null);

        //创建3个静态视图
        //加入容器
        addView(mLoadingView);
        addView(mEmptyView);
        addView(mErrorView);
        //错误视图点击重试找到点击重试按钮的id,再设置点击事件
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新触发加载
                triggerLoadData();
            }
        });
        //根据加载状态切换视图
        refreshUIByState();

    }
    //触发加载，触发加载对应的数据
    public void triggerLoadData() {
        if(mCurState != STATE_SUCCESS && mLoadDataTask == null) {
            //重置当前状态
            mCurState = STATE_LOADING;
            refreshUIByState();
        //异步加载
            mLoadDataTask = new LoadDataTask();
            //new Thread(mLoadDataTask).start();
            ThreadPoolProxyFactory.createNormalThreadProxy().submit(mLoadDataTask);
        }
    }

    //根据加载状态切换视图
    private void refreshUIByState() {
    //先隐藏
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        if(mSuccessView != null){
            mSuccessView.setVisibility(View.GONE);
        }
        switch (mCurState){
            case STATE_EMPTY:
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS:
                if(mSuccessView == null){
                    //初始化成功视图
                    mSuccessView = initSuccessView();
                    //加入容器
                    addView(mSuccessView);
                    //进行展示
                    mSuccessView.setVisibility(View.VISIBLE);
                }
                break;

        }
    }
/**
    1.初始化成功视图
    2.外界触发加载数据,而且数据加载成功了,而且成功视图没有创建
    3.在LoadingPager本类,不知道如何初始化具体的成功视图,只能交给子类去实
    4.子类必须实现,所以改成抽象方法,方法体改成public
 */
    public abstract View initSuccessView();
    //必须实现：在子线程总加载对应的数据
    public abstract LoadDataResult initData();

    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正的在子线程中加载对应的数据
            LoadDataResult loadDataResult = initData();
            int state = loadDataResult.state;
            //处理数据
            mCurState = state;
            //刷新ui的任务交给主线程来做
            MyApplication.getmHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->决定到底提供4中视图中的哪一种
                    refreshUIByState();
                }
            });
            //置空任务
            mLoadDataTask = null;
        }
    }

    //定义枚举控制4种视图切换
    public enum LoadDataResult{
        SUCCESS(STATE_SUCCESS),EMPTY(STATE_EMPTY),ERROR(STATE_ERROR);
        public int state;
        LoadDataResult(int state){
            this.state = state;
        }
    }
}
