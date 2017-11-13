package com.peng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.peng.base.BaseActivity;
import com.peng.base.ItemAdapter;
import com.peng.bean.DetailBean;
import com.peng.controller.LoadingPager;
import com.peng.holder.DetailDesHolder;
import com.peng.holder.DetailDownLoadHolder;
import com.peng.holder.DetailInfoHolder;
import com.peng.holder.DetailPicHolder;
import com.peng.holder.DetailSafeHolder;
import com.peng.protocol.DetailProtocol;
import com.peng.utils.UIUtils;

import java.io.IOException;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by peng on 2017/11/12.
 */

public class DetailActivity extends BaseActivity {
    @Bind(R.id.fl_download_container)
    FrameLayout mFlDownloadContainer;
    @Bind(R.id.fl_info_container)
    FrameLayout mFlInfoContainer;
    @Bind(R.id.fl_safe_container)
    FrameLayout mFlSafeContainer;
    @Bind(R.id.fl_pic_container)
    FrameLayout mFlPicContainer;
    @Bind(R.id.fl_des_container)
    FrameLayout mFlDesContainer;
    private String mPackageName;
    private DetailBean mDetailBean;

    //1.加载成功视图-->并数据和视图进行绑定
    @Override
    public View initSuccessView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.layout_detail, null);
        //1.6 空指针异常,butterknife没有bind填充的布局，因为以前是holder类里继承了BaseHolder，父类里有bind，所以不用bind，而这里是activity
        ButterKnife.bind(this,holderView);
        //1.然后每个holder进行实例化，添加到各自的容器，并且让holder接收数据，并刷新ui操作
        //1.1 应用的信息部分
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        mFlInfoContainer.addView(detailInfoHolder.mHolderView);
        detailInfoHolder.setDataAndRefreshHolderView(mDetailBean);
        //1.2 应用的安全部分
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mFlSafeContainer.addView(detailSafeHolder.mHolderView);
        detailSafeHolder.setDataAndRefreshHolderView(mDetailBean);
        //1.3 应用的截图部分
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        mFlPicContainer.addView(detailPicHolder.mHolderView);
        detailPicHolder.setDataAndRefreshHolderView(mDetailBean);
        //1.4 应用的描述部分
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        mFlDesContainer.addView(detailDesHolder.mHolderView);
        detailDesHolder.setDataAndRefreshHolderView(mDetailBean);
        //1.5 应用的下载部分
        DetailDownLoadHolder detailDownLoadHolder = new DetailDownLoadHolder();
        mFlDownloadContainer.addView(detailDownLoadHolder.mHolderView);
        detailDownLoadHolder.setDataAndRefreshHolderView(mDetailBean);
        return holderView;
    }

    //2.在子线程中加载数据的数据
    @Override
    public LoadingPager.LoadDataResult onInitData() {
        //2.1 创建DetailPotocol
        DetailProtocol protocol = new DetailProtocol(mPackageName);
        try {
            mDetailBean = protocol.loadData(0);
            return checkResData(mDetailBean);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadDataResult.ERROR;
        }
    }

    //3.接收参数
    @Override
    public void init() {
        mPackageName = getIntent().getStringExtra(ItemAdapter.PACKAGE_NAME);
        super.init();
    }
}
