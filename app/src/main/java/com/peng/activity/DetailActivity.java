package com.peng.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.peng.base.BaseActivity;
import com.peng.base.ItemAdapter;
import com.peng.bean.DetailBean;
import com.peng.bean.DownLoadInfo;
import com.peng.controller.LoadingPager;
import com.peng.holder.DetailDesHolder;
import com.peng.holder.DetailDownLoadHolder;
import com.peng.holder.DetailInfoHolder;
import com.peng.holder.DetailPicHolder;
import com.peng.holder.DetailSafeHolder;
import com.peng.manager.DownloadManager;
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
    private DetailDownLoadHolder mDetailDownLoadHolder;

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
    //4.观察者动态移除和添加，和手动发布最新状态
    @Override
    protected void onResume() {
        //4.1 动态添加观察者到观察者集合中
        if (mDetailDownLoadHolder != null) {
            DownloadManager.getInstance().addObserver(mDetailDownLoadHolder);

            //4.2手动发布DownLoadInfo的最新状态
            DownLoadInfo downLoadInfo = DownloadManager.getInstance().createDownLoadInfo(mDetailBean);
            DownloadManager.getInstance().notifyObservers(downLoadInfo);
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        //4.1 动态从观察者集合中移除观察者
        if (mDetailDownLoadHolder != null) {
            DownloadManager.getInstance().deleteObserver(mDetailDownLoadHolder);
        }
        super.onPause();
    }
    //5.actionbar
    @Override
    public void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("应用信息");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
    //5.1 acttionbar点击事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
