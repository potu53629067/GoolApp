package com.peng.holder;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.utils.UIUtils;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailDownLoadHolder extends BaseHolder<DetailBean> {
    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @Bind(R.id.app_detail_download_btn_download)
    Button mAppDetailDownloadBtnDownload;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_download, null);
        return holderView;
    }

    @Override
    public void refreshHolderView(DetailBean data) {

    }
    @OnClick(R.id.app_detail_download_btn_download)
    public void clickBtnDownLoad(){
        Toast.makeText(UIUtils.getContext(),"xiazai",Toast.LENGTH_SHORT).show();
    }
}
