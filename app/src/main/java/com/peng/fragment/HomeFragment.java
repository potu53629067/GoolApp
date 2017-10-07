package com.peng.fragment;


import android.view.View;

import android.widget.ListView;

import com.peng.protocol.HomeProtocol;
import com.peng.adapter.HomeAdapter;
import com.peng.base.BaseFragment;

import com.peng.bean.HomeBean;
import com.peng.bean.ItemBean;

import com.peng.controller.LoadingPager;

import com.peng.utils.UIUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 2017/9/13.
 */
public class HomeFragment extends BaseFragment {
    private List<ItemBean> mDatas;//shift+alt+r
    private List<String>   mPictures;
    private HomeProtocol mProtocol;

    @Override
    protected LoadingPager.LoadDataResult initData() {
        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);
            LoadingPager.LoadDataResult state = checkResData(homeBean);
            if(state != LoadingPager.LoadDataResult.SUCCESS){   //说明出了问题，对象为null
                return state;
            }
            state = checkResData(homeBean.list);
            if(state != LoadingPager.LoadDataResult.SUCCESS){   //说明除了问题，集合长度为0
                return state;
            }
            //一切正常-->保存数据到成员变量
            mDatas = homeBean.list;
            mPictures = homeBean.picture;
            return state;   //这个时候的状态就是一个成功状态
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }

    @Override
    protected View initSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        HomeAdapter adapter = new HomeAdapter(listView,mDatas);
        listView.setAdapter(adapter);
        return listView;
    }

}
