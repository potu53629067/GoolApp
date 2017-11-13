package com.peng.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.conf.Constants;
import com.peng.utils.StringUtils;
import com.peng.utils.UIUtils;
import com.squareup.picasso.Picasso;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailInfoHolder extends BaseHolder<DetailBean> {
    @Bind(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @Bind(R.id.app_detail_info_tv_name)
    TextView mAppDetailInfoTvName;
    @Bind(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @Bind(R.id.app_detail_info_tv_downloadnum)
    TextView mAppDetailInfoTvDownloadnum;
    @Bind(R.id.app_detail_info_tv_version)
    TextView mAppDetailInfoTvVersion;
    @Bind(R.id.app_detail_info_tv_time)
    TextView mAppDetailInfoTvTime;
    @Bind(R.id.app_detail_info_tv_size)
    TextView mAppDetailInfoTvSize;

    //1.提供的视图
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        return holderView;
    }

    //2.视图和数据绑定
    @Override
    public void refreshHolderView(DetailBean data) {
        mAppDetailInfoTvName.setText(data.name);
        //2.1 下载量
        String downloadNumStr = UIUtils.getResources().getString(R.string.detail_downloadnum,data.downloadNum);
        mAppDetailInfoTvDownloadnum.setText(downloadNumStr);

        mAppDetailInfoTvSize.setText("大小:" + StringUtils.formatFileSize(data.size));
        mAppDetailInfoTvTime.setText("时间:"+ data.date);
        mAppDetailInfoTvVersion.setText("版本:"+ data.version);

        //2.2 评分ratingbar
        mAppDetailInfoRbStar.setRating(data.stars);
        //2.3 图标
        Picasso.with(UIUtils.getContext()).load(Constants.URL.IMAGBURL+ data.iconUrl).into(mAppDetailInfoIvIcon);
    }
}
