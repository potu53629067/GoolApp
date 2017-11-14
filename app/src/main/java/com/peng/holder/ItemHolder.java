package com.peng.holder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.ItemBean;
import com.peng.conf.Constants;
import com.peng.utils.StringUtils;
import com.peng.utils.UIUtils;
import com.peng.views.ProgressView;
import com.squareup.picasso.Picasso;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/9/14.
 */
public class ItemHolder extends BaseHolder<ItemBean> {


    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        return holderView;
    }

    @Override
    public void refreshHolderView(ItemBean data) {

        mItemAppinfoTvDes.setText(data.des);
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvTitle.setText(data.name);
        //ratingBar(展示评分)
        mItemAppinfoRbStars.setRating(data.stars);
        //图标的加载
        Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL + data.iconUrl).into(mItemAppinfoIvIcon);
    }
}
