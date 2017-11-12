package com.peng.holder;

import android.view.View;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.CategoryBean;
import com.peng.utils.UIUtils;

/**
 * Created by peng on 2017/11/12.
 */

public class CategoryTitleHolder extends BaseHolder<CategoryBean> {
    private TextView mTextView;
    @Override
    public View initHolderViewAndFindViews() {
        mTextView = new TextView(UIUtils.getContext());
        int padding = UIUtils.dp2Px(5);
        mTextView.setPadding(padding,padding,padding,padding);
        return mTextView;
    }

    @Override
    public void refreshHolderView(CategoryBean data) {
        mTextView.setText(data.title);
    }
}
