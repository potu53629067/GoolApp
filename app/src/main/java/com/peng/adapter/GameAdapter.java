package com.peng.adapter;

import android.os.SystemClock;
import android.widget.AbsListView;

import com.peng.base.BaseHolder;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.ItemBean;
import com.peng.holder.ItemHolder;
import com.peng.protocol.GameProtocol;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 2017/10/3.
 */
public class GameAdapter extends SuperBaseAdapter<ItemBean> {
    GameProtocol mProtocol = new GameProtocol();
   public GameAdapter(AbsListView absListView,List<ItemBean> datas){
       super(absListView,datas);
    }
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
            loadMoreList =  mProtocol.loadData(mDatas.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadMoreList;
    }
}
