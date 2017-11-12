package com.peng.adapter;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.peng.base.ItemAdapter;
import com.peng.protocol.HomeProtocol;
import com.peng.base.BaseHolder;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.HomeBean;
import com.peng.bean.ItemBean;
import com.peng.holder.ItemHolder;
import com.peng.utils.UIUtils;

import java.io.IOException;
import java.util.List;


/**
 * Created by peng on 2017/9/14.
 */
public class HomeAdapter extends ItemAdapter {
    HomeProtocol mProtocol = new HomeProtocol();

    public HomeAdapter(AbsListView absListView, List<ItemBean> datas) {
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
            homeBean = mProtocol.loadData(mDatas.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (homeBean != null) {
            return homeBean.list;
        }
        return super.onLoadMore();
    }
}

