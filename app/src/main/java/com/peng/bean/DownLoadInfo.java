package com.peng.bean;

import com.peng.manager.DownloadManager;

/**
 * Created by peng on 2017/11/14.
 */

public class DownLoadInfo {
    public String downloadUrl;//下载地址
    public String packageName;//包名
    //监听这个状态的改变,更新ui,点击触发不同的操作
    public int    curState;
    public long max;
    public long progress;
    //取消下载--记录任务
    public DownloadManager.DownLoadTask downLoadTask;
}
