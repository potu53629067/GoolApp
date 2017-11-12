package com.peng.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.peng.factory.ThreadPoolProxyFactory;
import com.peng.holder.LoadMoreHolder;
import com.peng.utils.LogUtils;
import com.peng.utils.MyApplication;

import java.util.List;

/**
 * Created by peng on 2017/9/14.
 */
public abstract class SuperBaseAdapter<ITEMTYPE> extends MyBaseAdapter<ITEMTYPE> implements AdapterView.OnItemClickListener {
    public static final int VIEWTYPE_LOADMORE = 0;//加载更多的类型
    public static final int VIEWTYPE_NORMAL   = 1;//普通的条目类型
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private final AbsListView  mAbsListView;
    private int mState;

    public SuperBaseAdapter(AbsListView absListView, List<ITEMTYPE> mDatas){
        super(mDatas);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
             /*--------------- 决定ItemView长什么样子 ---------------*/
        //得到当前positon所对应的条目的ViewType类型
        int curItemViewType = getItemViewType(position);
        BaseHolder holder = null;
        if (convertView == null) {
            if(curItemViewType == VIEWTYPE_LOADMORE){
                //创建加载更多holder
                holder = getLoadMoreHolder();
            }else {
                //创建holder
                holder = getSpecialBaseHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        //得到数据,数据和视图的绑定
        if(curItemViewType == VIEWTYPE_LOADMORE){
            if(hasLoadMore()){
            //data
            int initState = LoadMoreHolder.LOADMORE_LOADING;
            //传递数据给LoadMoreHolder
            mLoadMoreHolder.setDataAndRefreshHolderView(initState);
            //触发加载更多的数据
            triggerLoadMoreData();
            }else{
                //没有加载更多时候的ui情况
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        }else{
            holder.setDataAndRefreshHolderView(mDatas.get(position));
            }
        return holder.mHolderView;
    }
    //决定是否有加载更多的情况交给子类复写
    public boolean hasLoadMore() {
        return false;//默认是没有
    }

    //加载更多的数据
    private void triggerLoadMoreData() {
        if (mLoadMoreTask == null){
         //重置状态，展示正在加载更多
         mState = LoadMoreHolder.LOADMORE_LOADING;
         mLoadMoreHolder.setDataAndRefreshHolderView(mState);
        LogUtils.s("###触发加载更多");
        //异步加载，得到数据，处理数据，刷新ui
        mLoadMoreTask = new LoadMoreTask();
        ThreadPoolProxyFactory.createNormalThreadProxy().submit(mLoadMoreTask);
        }
    }

    //得到BaseHolder的子类对象
    private LoadMoreHolder getLoadMoreHolder() {
        if(mLoadMoreHolder == null){
        mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }
    /**---------------  ListView中显示几种ViewType begin---------------*/
    /**
        1.重写2个方法
    	2.在getView方法中分别处理
     */

    /**
    getViewTypeCount()-->得到ViewType的总数,默认是1
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//1(普通类型)+1(加载更多)=2
    }

    /**
    getItemViewType(position)-->得到指定postion所对应条目的ViewType类型
    0 to getViewTypeCount - 1-->0-1==>0 1
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else {
            return getNormalItemViewtype(position);
        }
    }
    //得到普通条目的ViewType的类型：子类可以复写该方法，返回更多的普通条目类型
    public int getNormalItemViewtype(int postion) {
        return VIEWTYPE_NORMAL; //默认值
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }
    /**---------------  ListView中显示几种ViewType end---------------*/

    /**
     * @return
     * @des 返回具体的BaseHolder的子类对象
     * @des 在SuperBaseAdapter中不知道如何返回具体的BaseHolder的子类对象, 只能交给子类
     * @des 子类是必须实现, 所以定义成为抽象方法即可
     * @param position
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);

    //处理条目的点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //18.有头情况下的postion处理
        if(mAbsListView instanceof ListView){
            position = position - ((ListView)mAbsListView).getHeaderViewsCount();
        }
    int curItemViewType = getItemViewType(position);
    if(curItemViewType == VIEWTYPE_LOADMORE){
        if(mState == LoadMoreHolder.LOADMORE_ERROR){
            //重新触发加载更多的数据
            triggerLoadMoreData();
        }
    }else{
        //普通条目的点击事件
        onNormalItemClick(parent,view,position,id);
    }
    }
    //普通条目的点击事件;子类选择实现
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    private class LoadMoreTask implements Runnable {
        private static final int PAGESIZE = 20;

        @Override
        public void run() {
        //定义决定ui两个的两个数据
        List<ITEMTYPE> loadMoreList = null;
            mState = 0;

            try {
                //真正的在子线程中具体加载更多的数据
                loadMoreList = onLoadMore();
                //处理数据
                if(loadMoreList == null){
                    mState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                }else{
                    if(loadMoreList.size() == PAGESIZE){
                        mState = LoadMoreHolder.LOADMORE_LOADING;//下次用户看到的还是正在加载更多的情况
                    }else{
                        mState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.LOADMORE_ERROR;//加载更多失败
            }
            final List<ITEMTYPE> finalLoadMoreList = loadMoreList;
            final int finalState = mState;
            MyApplication.getmHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新-->ListView-->更新数据源，然后刷新数据-->List
                    if(finalLoadMoreList !=null && finalLoadMoreList.size()>0){
                        //更新数据源
                        mDatas.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                        //刷新-->LoadMoreHolder-->传递最新状态-->int
                        mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                    }
                }
            });
            //置空任务
            mLoadMoreTask = null;
        }
    }
    /**
     * @return
     * @des 真正的在子线程中加载更多的数据
     * @des 在SuperBaseAdapter中, 不知道如何具体加载更多的数据, 只能交给子类
     * @des 子类选择性实现
     */
    public List<ITEMTYPE> onLoadMore() {
        return null;
    }


}
