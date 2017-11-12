package com.peng.adapter;

import android.widget.AbsListView;

import com.peng.base.BaseHolder;
import com.peng.base.SuperBaseAdapter;
import com.peng.bean.CategoryBean;
import com.peng.holder.CategoryTitleHolder;
import com.peng.holder.CategoryNormalHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class CategoryAdapter extends SuperBaseAdapter<CategoryBean> {
    public CategoryAdapter(AbsListView absListView, List<CategoryBean> mDatas) {
        super(absListView, mDatas);
    }
    @Override
    public BaseHolder getSpecialBaseHolder(int postion) {
        //1.得到每一个条目对应的ItemBean
        CategoryBean categoryBean = mDatas.get(postion);
            if (categoryBean.isTitle){
                return new CategoryTitleHolder();
            }else{
                return new CategoryNormalHolder();
            }
        }
}
