package com.peng.utils;


import com.peng.bean.DownLoadInfo;
import com.peng.manager.DownloadManager;

/**
 * @author Administrator
 * @version $Rev: 77 $
 * @time 2015-7-13 下午11:12:12
 * @des TODO
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2016-08-27 14:28:58 +0800 (周六, 27 8月 2016) $
 * @updateDes TODO
 */
public class PrintDownLoadInfo {
    public static void printDownLoadInfo(DownLoadInfo info) {
        String result = "";
        switch (info.curState) {
            case DownloadManager.STATE_UNDOWNLOAD:// 未下载
                result = "未下载";
                break;
            case DownloadManager.STATE_DOWNLOADING:// 下载中
                result = "下载中";
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
                result = "暂停下载";
                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
                result = "等待下载";
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
                result = "等待下载";
                break;
            case DownloadManager.STATE_DOWNLOADED:// 下载完成
                result = "下载完成";
                break;
            case DownloadManager.STATE_INSTALLED:// 已安装
                result = "已安装";
                break;

            default:
                break;
        }
        LogUtils.sf(result);
    }
}
