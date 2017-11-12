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

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;
    }

    /* @Override
        public int getItemViewType(int position) {
            CategoryBean itemBean = mDatas.get(position);
            if (position == getCount() - 1) {
                return VIEWTYPE_LOADMORE;//0
            } else {
                if (itemBean.isTitle) {
                    return 1;
                } else {
                    return 2;
                }
            }

            //加载更多
            //普通
            //title
            //normal
//          return super.getItemViewType(position);
        }*/
        /*--------------- 方式二 ---------------*/
    /**
     * 返回普通条目具体的ViewType类型
     * @return
     */
    @Override
    public int getNormalItemViewtype(int postion) {
            CategoryBean itemBean = mDatas.get(postion);
            if (itemBean.isTitle){
                return 1;
            }else{
                return 2;
            }
    }
}
