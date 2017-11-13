package com.peng.holder;

import android.view.View;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.utils.UIUtils;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailDownLoadHolder extends BaseHolder<DetailBean> {
    @Override
    public View initHolderViewAndFindViews() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText("");
        return tv;
    }

    @Override
    public void refreshHolderView(DetailBean data) {

    }
}
