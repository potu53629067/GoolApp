package com.peng.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.peng.controller.LoadingPager;
import com.peng.utils.CheckDataUtils;

/**
 * Created by peng on 2017/11/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private LoadingPager mLoadingPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.必须实现的方法-setContentView();
        setContentView(initContentView());
        //2.加载数据
        initData();
        //3.接收参数
        init();
        //4.处理ActionBar
        initActionBar();
        //5.监听器
        initListener();
    }

    /**
     * @return
     * @des 1.1 初始化每一个Activity都应该提供的视图
     */
    public View initContentView() {
        mLoadingPager = new LoadingPager(this) {
            /**
             * @des 1.2 在子线程中真正的加载数据
             * @return
             */
            @Override
            public LoadDataResult initData() {
                return BaseActivity.this.onInitData();
            }

            /**
             * @des 1.3 决定成功视图是啥, 以及进行数据和视图的绑定
             * @return
             */
            @Override
            public View initSuccessView() {
                return BaseActivity.this.initSuccessView();
            }
        };
        return mLoadingPager;
    }
        /**
         * @return
         * @des 1.4 决定成功视图是啥, 以及如何进行数据的绑定
         * @des 在BaseActivity中不知道成功视图是啥, 以及如何进行数据的绑定只能交给子类
         * @des 子类是必须实现, 定义成为抽象方法即可
         */
        public abstract View initSuccessView();
    /**
     * 2.1 加载数据
     */
    public void initData() {
        //触发异步加载数据即可
        mLoadingPager.triggerLoadData();
    }
    /**
     * @return
     * @des 2.2 在子线程中真正的加载数据
     * @des 在BaseActivity中不知道如何具体加载数据, 交给子类
     * @des 子类是必须实现, 定义成为抽象方法即可
     */
    public abstract LoadingPager.LoadDataResult onInitData();
    //3.接收参数
    public void init() {
    }
    //4.处理ActionBar
    public void initActionBar() {
    }
    //5.监听器
    public void initListener() {
    }
    /**
     * 6.校验请求回来的数据,返回对应的状态
     */
    public LoadingPager.LoadDataResult checkResData(Object resData) {
        return CheckDataUtils.checkResData(resData);
    }
}