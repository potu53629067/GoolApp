package com.peng.fragment;

import android.view.View;
import android.widget.ListView;

import com.peng.adapter.AppAdapter;
import com.peng.base.BaseFragment;
import com.peng.bean.ItemBean;
import com.peng.controller.LoadingPager;
import com.peng.factory.ListViewFactory;
import com.peng.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 2017/9/13.
 */
public class AppFragment extends BaseFragment {

    private AppProtocol mProtocol;
    private List<ItemBean> mDatas;

    @Override
    protected LoadingPager.LoadDataResult initData() {
        mProtocol = new AppProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkResData(mDatas);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }

    @Override
    protected View initSuccessView() {
        //view
        ListView listView = ListViewFactory.createListView();
        //data-->成员变量

        //data+view
        AppAdapter appAdpter = new AppAdapter(listView,mDatas);
        listView.setAdapter(appAdpter);

        return listView;
    }
}