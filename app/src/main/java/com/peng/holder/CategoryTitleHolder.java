package com.peng.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.CategoryBean;
import com.peng.utils.UIUtils;

import java.util.Random;

/**
 * Created by peng on 2017/11/12.
 */

public class CategoryTitleHolder extends BaseHolder<CategoryBean> {
    private TextView mTvTitle;
    @Override
    public View initHolderViewAndFindViews() {
        mTvTitle = new TextView(UIUtils.getContext());
        //设置间距
        int padding = UIUtils.dp2Px(5);
        mTvTitle.setPadding(padding,padding,padding,padding);
        return mTvTitle;
    }
    @Override
    public void refreshHolderView(CategoryBean data) {
        Random random = new Random();
        //随机颜色
        int alpha = 255;//0-255
        int red = random.nextInt(121) + 100; //100-220
        int green = random.nextInt(121) + 100; //100-220
        int blue = random.nextInt(121) + 100; //100-220
        int color = Color.argb(alpha,red,green,blue);
        mTvTitle.setTextColor(color);
        mTvTitle.setText(data.title);
    }
}
