package com.peng.manager;

import com.peng.bean.DetailBean;
import com.peng.bean.DownLoadInfo;
import com.peng.conf.Constants;
import com.peng.factory.ThreadPoolProxyFactory;
import com.peng.utils.CommonUtils;
import com.peng.utils.FileUtils;
import com.peng.utils.HttpUtils;
import com.peng.utils.IOUtils;
import com.peng.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peng on 2017/11/14.
 */

public class DownloadManager {
    private static DownloadManager instance;
    //缓存downLoadInfo,用户点击下载按钮之后的DownLoadInfo
    private Map<String, DownLoadInfo> mCacheDownLoadInfo = new HashMap<>();

    public static final int STATE_UNDOWNLOAD      = 0;//未下载
    public static final int STATE_DOWNLOADING     = 1;//下载中
    public static final int STATE_PAUSEDOWNLOAD   = 2;//暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;//等待下载
    public static final int STATE_DOWNLOADFAILED  = 4;//下载失败
    public static final int STATE_DOWNLOADED      = 5;//下载完成
    public static final int STATE_INSTALLED       = 6;//已安装

    private DownloadManager(){
    }
    public static DownloadManager getInstance(){
        if (instance == null){
            synchronized (DownloadManager.class){
                if (instance == null){
                    instance = new DownloadManager();
                }
            }
        }
        return  instance;
    }
    //1.根据DetailBean的详细信息，创建一个与之对应的DownLoadInfo
    public DownLoadInfo createDownLoadInfo(DetailBean detailBean) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        //1.1 常规赋值
        downLoadInfo.downloadUrl = detailBean.downloadUrl;
        downLoadInfo.packageName = detailBean.packageName;
        downLoadInfo.max = detailBean.size;
        downLoadInfo.progress = 0;
        //4.5 重要赋值
        // 已安装-->用户即使不点击下载按钮,手机里面安装了对应的apk
        if (CommonUtils.isInstalled(UIUtils.getContext(), downLoadInfo.packageName)) {
            downLoadInfo.curState = STATE_INSTALLED;
            notifyObservers(downLoadInfo);
            return downLoadInfo;
        }
        //4.6 下载完成-->如果用户曾经下载完成过,用户无需点击下载按钮,状态也应该是下载完成
        String dir = FileUtils.getDir("apk");
        String fileName = downLoadInfo.packageName + ".apk";
        File saveFile = new File(dir, fileName);
        if (saveFile.exists() && saveFile.length() == detailBean.size) {
            downLoadInfo.curState = STATE_DOWNLOADED;
            notifyObservers(downLoadInfo);
            return downLoadInfo;
        }
        //4.7 5种情况
        if (mCacheDownLoadInfo.containsKey(detailBean.packageName)) {
            //说明,ItemBean对应的apk,曾经被下载过,当前的状态可能是5种中的一种
            return mCacheDownLoadInfo.get(detailBean.packageName);
        }
        //4.8 未下载-->默认
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
        return downLoadInfo;
    }
    //2.开始下载
    public void downLoad(DownLoadInfo downLoadInfo) {
        //4.9 map保存状态作为缓存
        mCacheDownLoadInfo.put(downLoadInfo.packageName, downLoadInfo);
        //4.当前状态为未下载
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
        //4.1 预先把状态设置为等待中
        downLoadInfo.curState = STATE_WAITINGDOWNLOAD;
        notifyObservers(downLoadInfo);
        //2.1 异步下载
        //8.2 记录任务，赋值任务
        DownLoadTask task = new DownLoadTask(downLoadInfo);
        ThreadPoolProxyFactory.createDownloadThreadProxy().submit(task);
        //8.2 对DownLoadInfo里面的task赋值
        downLoadInfo.downLoadTask = task;
    }
    //6.暂停下载
    public void pause(DownLoadInfo downLoadInfo) {
        downLoadInfo.curState = STATE_PAUSEDOWNLOAD;
        notifyObservers(downLoadInfo);
    }
    //8.取消下载
    public void cancel(DownLoadInfo downLoadInfo) {
        //8.1 未下载
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
        //8.2 从线程池移除任务
        ThreadPoolProxyFactory.createDownloadThreadProxy().remove(downLoadInfo.downLoadTask);
    }

    //2.2 下载的子线程
    public class DownLoadTask implements Runnable{
         private DownLoadInfo mDownLoadInfo;

         public DownLoadTask(DownLoadInfo downLoadInfo) {
            this.mDownLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {
        //4.2 当前状态：下载中
        mDownLoadInfo.curState = STATE_DOWNLOADED;
         notifyObservers(mDownLoadInfo);
         //7.断点续传
         long initRange = 0;
         String dir = FileUtils.getDir("apk");
         String fileName = mDownLoadInfo.packageName+".apk";
         File saveFile = new File(dir,fileName);
         if(saveFile.exists()){
             initRange = saveFile.length();
             //7.1 downLoadInfo里面的progess需要进行初始化为initRange
             mDownLoadInfo.progress = initRange;
         }
        InputStream in = null;
        BufferedOutputStream out = null;
     try {
        //3.正在子线程中开始下载
        //3.1 创建okHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
        //3.2 创建请求对象
        String url = Constants.URL.BASEURL + "download";
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("name",mDownLoadInfo.downloadUrl);
        paramsMap.put("range",initRange); //7.2 发起请求的时候,range需要传入具体的初始值
        //转换参数，拼接参数
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);
        url = url + "?" + urlParamsByMap;

        Request request = new Request.Builder().get().url(url).build();
        //3.3 发起请求,得到结果
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    //得到结果流，处理结果
                    in  = response.body().byteStream();
                    //把流转换成文件
                    out = new BufferedOutputStream(new FileOutputStream(saveFile,true));
                    int len = -1;
                    byte[] buffer  = new byte[1024];
                    while ((len = in.read(buffer)) != -1){
                        //6.1 根据暂停状态来跳出下载写入的循环
                        if (mDownLoadInfo.curState == STATE_PAUSEDOWNLOAD){
                            break;
                        }
                        out.write(buffer,0,len);
                        //4.2 当前状态：下载中
                        mDownLoadInfo.progress += len;
                        mDownLoadInfo.curState = STATE_DOWNLOADED;
                        notifyObservers(mDownLoadInfo);
                        //7.3 下载完成,就提前跳出while循环
                        if (mDownLoadInfo.progress == mDownLoadInfo.max){
                            break;
                        }
                    }
                    //6.2 bug:会跳到下载完成，不是暂停状态才跳
                    if (mDownLoadInfo.curState == STATE_PAUSEDOWNLOAD){

                    }else{
                        //4.3 下载完成--当前状态：下载完成状态
                        mDownLoadInfo.curState = STATE_DOWNLOADED;
                        notifyObservers(mDownLoadInfo);
                    }

                }else { //4.4 响应失败:下载失败
                    mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
                    notifyObservers(mDownLoadInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //4.4 异常也是：下载失败
                mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
                notifyObservers(mDownLoadInfo);
            }finally {
                IOUtils.close(out);
                IOUtils.close(in);
            }
        }
    }
//5.自己实现观察者设计模式,传递DownLoadInfo出去
//5.1 定义接口，以及接口方法
    public interface DownloadInfoObserver{
        void onDownloadInfoChanged(DownLoadInfo downLoadInfo);
    }
//5.2 定义集合保存所有的接口对象
    private List<DownloadInfoObserver> observers = new ArrayList<>();
//5.3 添加观察者到观察者集合中
    public synchronized void addObserver(DownloadInfoObserver o){
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)){
            observers.add(o);
        }
    }
//5.4 从观察者集合中移除观察者
    public synchronized  void deleteObserver(DownloadInfoObserver o){
        observers.remove(o);
    }
//5.5 通知所有的观察者
    public void notifyObservers(DownLoadInfo downLoadInfo){
        for (DownloadInfoObserver o: observers) {
            o.onDownloadInfoChanged(downLoadInfo);
        }
    }
}
