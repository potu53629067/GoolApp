package com.peng.base;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by peng on 2017/9/14.
 */
public abstract class BaseHolder<TYPE> {
    //视图的定义
    public View mHolderView;
    //数据的定义
    public TYPE mData;

    public BaseHolder(){
        //初始化提供的视图
        mHolderView = initHolderViewAndFindViews();
        //通过注解的方式找出具体的孩子
        ButterKnife.bind(this,mHolderView);
        //找一个holder,然后绑定在itemView上面
        mHolderView.setTag(this);
    }
    //接收数据，并且数据和视图的绑定
    public void setDataAndRefreshHolderView(TYPE data){
        //保存数据到成员变量
        mData = data;
        //数据和视图的绑定
        refreshHolderView(data);
    }
    /**
     * @return
     * @des 初始化所能提供的视图
     * @des 在BaseHolder不知道如何初始化所能提供的视图, 更不知道知道如何找出孩子, 只能交给子类
     * @des 子类是必须实现, 定义成为抽象方法即可
     */
    public abstract View initHolderViewAndFindViews();
    /**
     * @param data
     * @des 数据和视图的绑定
     * @des 在BaseHolder中不知道如何进行数据和视图的绑定, 只能交给子类
     * @des 子类必须实现, 所以定义成为抽象方法即可
     */
    public abstract void refreshHolderView(TYPE data);
}
