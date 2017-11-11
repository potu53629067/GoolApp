package com.peng.adapter;

import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class HomeAdapter extends SuperBaseAdapter<ItemBean> {
    HomeProtocol mProtocol = new HomeProtocol();

    public HomeAdapter(ListView listView, List<ItemBean> datas) {
        super(listView,datas);
    }

    @Override
    public BaseHolder getSpecialBaseHolder() {
        return new ItemHolder();
    }
    //决定有加载更多
    @Override
    public boolean hasLoadMore() {
        return true;
    }
    //决定具体如何加载更多

    @Override
    public List<ItemBean> onLoadMore() {
        SystemClock.sleep(2000);
        try {
            HomeBean homeBean = mProtocol.loadData(mDatas.size());
            if(homeBean !=null){
                return homeBean.list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onLoadMore();
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //data
        ItemBean itemBean = mDatas.get(position);
        Toast.makeText(UIUtils.getContext(), itemBean.name, Toast.LENGTH_SHORT).show();
        super.onNormalItemClick(parent, view, position, id);
    }
}

