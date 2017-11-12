package com.peng.adapter;

import android.widget.AbsListView;

import com.peng.base.BaseHolder;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.SubjectInfo;
import com.peng.holder.SubjectHolder;

import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class SubjectAdapter extends SuperBaseAdapter<SubjectInfo> {
    public SubjectAdapter(AbsListView absListView, List mDatas) {
        super(absListView, mDatas);
    }

    @Override
    public BaseHolder getSpecialBaseHolder() {
        return new SubjectHolder();
    }
}
