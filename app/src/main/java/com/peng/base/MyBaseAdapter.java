package com.peng.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by peng on 2017/9/14.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mDatas;

    public MyBaseAdapter(List<T> mDatas){
        this.mDatas = mDatas;
    }
    @Override
    public int getCount() {
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mDatas != null){
            return mDatas.get(position);
        }
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
