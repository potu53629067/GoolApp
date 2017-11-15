package com.peng.holder;

import android.view.View;
import android.widget.Button;

import com.peng.base.BaseHolder;
import com.peng.bean.DetailBean;
import com.peng.bean.DownLoadInfo;
import com.peng.manager.DownloadManager;
import com.peng.utils.CommonUtils;
import com.peng.utils.FileUtils;
import com.peng.utils.MyApplication;
import com.peng.utils.PrintDownLoadInfo;
import com.peng.utils.UIUtils;
import com.peng.views.ProgressButton;

import java.io.File;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailDownLoadHolder extends BaseHolder<DetailBean> implements DownloadManager.DownloadInfoObserver {
    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @Bind(R.id.app_detail_download_btn_download)
    ProgressButton mAppDetailDownloadBtnDownload;
    private DetailBean mDetailBean;

    //1.view
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_download, null);
        return holderView;
    }

    //2.view+data
    @Override
    public void refreshHolderView(DetailBean detailBean) {
        //无需绑定
        //2.1 保存数据到成员变量
        mDetailBean = detailBean;
        //5.根据不同的状态给用户提示
        //curState-->DownLoadInfo
        DownLoadInfo downLoadInfo = DownloadManager.getInstance().createDownLoadInfo(mDetailBean);
        refreshProgressBtnUI(downLoadInfo);
    }
    //6.2 抽成刷新ui的方法
    private void refreshProgressBtnUI(DownLoadInfo downLoadInfo) {
        mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (downLoadInfo.curState) {
            case DownloadManager.STATE_UNDOWNLOAD://未下载
                mAppDetailDownloadBtnDownload.setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING://下载中
                mAppDetailDownloadBtnDownload.setProgressEnable(true);
                mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                int index = (int) (downLoadInfo.progress * 100.f / downLoadInfo.max + .5f);
                mAppDetailDownloadBtnDownload.setText(index + "%");
                mAppDetailDownloadBtnDownload.setMax(downLoadInfo.max);
                mAppDetailDownloadBtnDownload.setProcess(downLoadInfo.progress);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD://暂停下载
                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD://等待下载
                mAppDetailDownloadBtnDownload.setText("等待中...");
                break;
            case DownloadManager.STATE_DOWNLOADFAILED://下载失败
                mAppDetailDownloadBtnDownload.setText("重试");
                break;
            case DownloadManager.STATE_DOWNLOADED://下载完成
                mAppDetailDownloadBtnDownload.setText("安装");
                mAppDetailDownloadBtnDownload.setProgressEnable(false);
                break;
            case DownloadManager.STATE_INSTALLED://已安装
                mAppDetailDownloadBtnDownload.setText("打开");
                break;
            default:
                break;
        }
    }


    //3.根据不同的状态触发不同的操作
    @OnClick(R.id.app_detail_download_btn_download)
    public void clickBtnDownLoad() {
        //Toast.makeText(UIUtils.getContext(),"xiazai",Toast.LENGTH_SHORT).show();
        //3.1 异步下载，得到数据，处理数据，刷新ui
        DownLoadInfo downLoadInfo = DownloadManager.getInstance().createDownLoadInfo(mDetailBean);
        //5.1 然后分别创建doDownload,doPause,doCancel,doInstallApk,doOpenApk方法
        switch (downLoadInfo.curState) {
            case DownloadManager.STATE_UNDOWNLOAD://未下载
                doDownload(downLoadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADING://下载中
                doPause(downLoadInfo);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD://暂停下载
                doDownload(downLoadInfo);
                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD://等待下载
                doCancel(downLoadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADFAILED://下载失败
                doDownload(downLoadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADED://下载完成
                doInstallApk(downLoadInfo);
                break;
            case DownloadManager.STATE_INSTALLED://已安装
                doOpenApk(downLoadInfo);
                break;

            default:
                break;
        }
    }

    /**
     * 打开apk
     *
     * @param downLoadInfo
     */
    private void doOpenApk(DownLoadInfo downLoadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);//方法在CommonUtils工具类封装好了
    }

    /**
     * 安装apk
     *
     * @param downLoadInfo
     */
    private void doInstallApk(DownLoadInfo downLoadInfo) {
        String dir = FileUtils.getDir("apk");
        String fileName = downLoadInfo.packageName + ".apk";
        File saveFile = new File(dir, fileName);
        CommonUtils.installApp(UIUtils.getContext(), saveFile);//方法在CommonUtils工具类封装好了
    }

    /**
     * 取消下载
     *
     * @param downLoadInfo
     */
    private void doCancel(DownLoadInfo downLoadInfo) {
        DownloadManager.getInstance().cancel(downLoadInfo);
    }

    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    private void doPause(DownLoadInfo downLoadInfo) {
    DownloadManager.getInstance().pause(downLoadInfo);
    }

    /**
     * 开始下载,断点继续下载,重试下载
     *
     * @param downLoadInfo
     */
    private void doDownload(DownLoadInfo downLoadInfo) {
        DownloadManager.getInstance().downLoad(downLoadInfo);
    }
    //6.观察者接收消息
    @Override
    public void onDownloadInfoChanged(final DownLoadInfo downLoadInfo) {
        //7.BUG:两个APP一起下载的时候,进度条,一起再动,(覆盖在一起显示了)
        if (!downLoadInfo.packageName.equals(mDetailBean.packageName)){
            return;
        }
        PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
        //刷新ui
        MyApplication.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                refreshProgressBtnUI(downLoadInfo);
            }
        });
    }
}