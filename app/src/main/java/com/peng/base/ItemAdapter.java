package com.peng.base;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.peng.activity.DetailActivity;
import com.peng.bean.ItemBean;
import com.peng.holder.ItemHolder;
import com.peng.utils.UIUtils;

import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class ItemAdapter extends SuperBaseAdapter<ItemBean> {

    public static final String PACKAGE_NAME = "packageName";

    public ItemAdapter(AbsListView absListView, List<ItemBean> mDatas) {
        super(absListView, mDatas);
    }

    @Override
    public BaseHolder getSpecialBaseHolder(int position) {
        return new ItemHolder();
    }
    /**
     * 1.决定有加载更多
     */
    @Override
    public boolean hasLoadMore() {
        return true;
    }

    /**
     * 2.处理条目的点击事件
     */
    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        //data
        ItemBean itemBean = mDatas.get(position);

        //Toast.makeText(UIUtils.getContext(), itemBean.name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACKAGE_NAME, itemBean.packageName);
        UIUtils.getContext().startActivity(intent);

        super.onNormalItemClick(parent, view, position, id);
    }
}
