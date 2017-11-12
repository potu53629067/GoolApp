package com.peng.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.SubjectInfo;
import com.peng.conf.Constants;
import com.peng.utils.UIUtils;
import com.squareup.picasso.Picasso;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/12.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {
    @Bind(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @Bind(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        return holderView;
    }

    @Override
    public void refreshHolderView(SubjectInfo data) {
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL + data.url).into(mItemSubjectIvIcon);
    }
}
