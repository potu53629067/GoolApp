package com.peng.adapter;

import android.os.SystemClock;
import android.widget.AbsListView;

import com.peng.base.BaseHolder;
import com.peng.base.ItemAdapter;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.HomeBean;
import com.peng.bean.ItemBean;
import com.peng.holder.ItemHolder;
import com.peng.protocol.AppProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 2017/10/3.
 */
public class AppAdapter extends ItemAdapter {
    AppProtocol mProtocol = new AppProtocol();
    public AppAdapter(AbsListView absListView, List<ItemBean> datas) {
        super(absListView, datas);
    }

    /**
     * 决定具体如何加载更多
     */
    @Override
    public List<ItemBean> onLoadMore() {
        SystemClock.sleep(1000);
        HomeBean homeBean = null;
        try {
            homeBean = (HomeBean) mProtocol.loadData(mDatas.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (homeBean != null) {
            return homeBean.list;
        }
        return super.onLoadMore();
    }
}
