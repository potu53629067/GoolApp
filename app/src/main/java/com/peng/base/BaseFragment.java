package com.peng.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.controller.LoadingPager;
import com.peng.utils.UIUtils;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by peng on 2017/9/13.
 */
public abstract class BaseFragment extends Fragment{

    private Context mContext;
    private LoadingPager mLoadingPager;


    public LoadingPager getLoadingPager(){
        return mLoadingPager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化操作，可以用来接收传递过来的参数
        mContext = getActivity();
        init();
        super.onCreate(savedInstanceState);
    }

    private void init() {
       /**
        1.进行一些初始化的操作
        2.在BaseFragment中不知道具体如何进行一些初始化的操作所以交给子类
        3.子类是选择性实现
        */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }

                @Override
                public LoadDataResult initData() {
                    return BaseFragment.this.initData();
                }
             };}
        return mLoadingPager;
        }
        //加载数据
    protected abstract LoadingPager.LoadDataResult initData();

    //成功视图
    protected abstract View initSuccessView();



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initListener();
        super.onActivityCreated(savedInstanceState);
    }



    private void initListener() {
       /**
        1.初始化相应的监听器
        2.在BaseFragment中不知道 初始化相应的监听器 所以交给子类
        3.子类是选择性实现*/
    }

    //效验请求回来的数据，返回对应的状态
    public LoadingPager.LoadDataResult checkResData(Object resData){
        if(resData == null) {
            return LoadingPager.LoadDataResult.EMPTY;
        }
        //resData 是集合-->List
        if(resData instanceof List){
            if(((List) resData).size()==0){
            return LoadingPager.LoadDataResult.EMPTY;
            }
        }
        //resData 是集合-->Map
        if(resData instanceof Map){
            if(((Map) resData).size()==0){
                return LoadingPager.LoadDataResult.EMPTY;
            }
        }
        return LoadingPager.LoadDataResult.SUCCESS;
    }
}
