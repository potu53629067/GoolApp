package com.peng.adapter;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.widget.AbsListView;

import com.peng.base.BaseHolder;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.ItemBean;
import com.peng.holder.ItemHolder;
import com.peng.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 2017/10/3.
 */
public class AppAdapter extends SuperBaseAdapter<ItemBean> {
    AppProtocol mProtocol = new AppProtocol();
    public AppAdapter(AbsListView absListView, List<ItemBean> datas){
        super(absListView,datas);
    }
    @NonNull
    @Override
    public BaseHolder getSpecialBaseHolder() {
        return new ItemHolder();
    }

    @Override
    public boolean hasLoadMore() {
        return true;
    }

    @Override
    public List<ItemBean> onLoadMore() {
        SystemClock.sleep(2000);
        List<ItemBean> loadMoreList = null;
        try {
            loadMoreList = mProtocol.loadData(mDatas.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadMoreList;
    }
}
