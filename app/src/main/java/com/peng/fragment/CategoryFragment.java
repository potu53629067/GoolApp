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

import com.peng.adapter.CategoryAdapter;
import com.peng.base.BaseFragment;
import com.peng.bean.CategoryBean;
import com.peng.controller.LoadingPager;
import com.peng.factory.ListViewFactory;
import com.peng.protocol.CategoryProtocol;
import com.peng.utils.UIUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by peng on 2017/9/13.
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryBean> mDatas;

    @Override
    protected LoadingPager.LoadDataResult initData() {
        CategoryProtocol protocol = new CategoryProtocol();
        try {
            mDatas = protocol.loadData(0);
            return checkResData(mDatas);

        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }

    @Override
    protected View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        CategoryAdapter adapter = new CategoryAdapter(listView,mDatas);
        listView.setAdapter(adapter);
        return listView;
    }
}