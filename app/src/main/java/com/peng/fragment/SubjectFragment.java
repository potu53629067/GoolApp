package com.peng.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.peng.adapter.SubjectAdapter;
import com.peng.base.BaseFragment;
import com.peng.bean.SubjectInfo;
import com.peng.controller.LoadingPager;
import com.peng.factory.ListViewFactory;
import com.peng.protocol.SubjectProtocol;
import com.peng.utils.UIUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by peng on 2017/9/13.
 */
public class SubjectFragment extends BaseFragment {
    SubjectProtocol mProtocol;
    List<SubjectInfo> mData;
    //1.效验数据，返回哪种视图
    @Override
    protected LoadingPager.LoadDataResult initData() {
        mProtocol  = new SubjectProtocol();
        try {
            mData = mProtocol.loadData(0);
            return checkResData(mData);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }
    //2.加载成功视图到Fragment容器
    @Override
    protected View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        SubjectAdapter adapter = new SubjectAdapter(listView,mData);
        listView.setAdapter(adapter);
        return listView;
    }
}