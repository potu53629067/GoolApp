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

import com.peng.adapter.GameAdapter;
import com.peng.base.BaseFragment;
import com.peng.bean.ItemBean;
import com.peng.controller.LoadingPager;
import com.peng.factory.ListViewFactory;
import com.peng.protocol.GameProtocol;
import com.peng.utils.UIUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by peng on 2017/9/13.
 */
public class GameFragment extends BaseFragment {

    private GameProtocol mProtocol;
    private List<ItemBean> mDatas;

    @Override
    protected LoadingPager.LoadDataResult initData() {
        mProtocol = new GameProtocol();
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
        ListView listView = ListViewFactory.createListView();
        GameAdapter adapter = new GameAdapter(listView,mDatas);
        listView.setAdapter(adapter);
        return listView;
    }
}