package com.peng.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.peng.bean.HomeBean;
import com.peng.conf.Constants;
import com.peng.utils.FileUtils;
import com.peng.utils.HttpUtils;
import com.peng.utils.IOUtils;
import com.peng.utils.LogUtils;
import com.peng.utils.MyApplication;
import com.peng.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 2017/9/16.
 * 封装网络协议
 */
public abstract class BaseProtocol<T> {

    public static final int PROTOCOLTIMETOU = 5 * 60 * 1000;
    public T loadData(int index) throws IOException {
        T result = null;

        //3.从内存，返回数据
        result = loadDataFromMem(index);
        if(result != null){
            LogUtils.s("###从内存加载了数据--->"+generateKey(index));
            return result;
        }
        //2.从本地加载数据
        result = loadDataFromLocal(index);
        if(result != null){
            LogUtils.s("###从本地加载了数据--->"+getCacheFile(index).getAbsolutePath());
            return result;
        }
        //1.从网络，返回，存本地
        return loadDataFromNet(index);
    }
    //1.从网络加载数据
    private T loadDataFromNet(int index) throws IOException {
        //1.创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.创建请求对象
        String url = Constants.URL.BASEURL + getInterfaceKey();
        //参数部分
        Map<String, Object> paramsMap = getParamsMap(index);
        //map-->String
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);
        //拼接参数
        url = url + "?" + urlParamsByMap;
        Request request = new Request.Builder().get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {//响应成功
            String resJsonString = response.body().string();
            //保存数据到本地
            writeData2Local(resJsonString,index);
            //保存数据到内存
            writeData2Men(resJsonString,index);
            //解析json
            Gson gson = new Gson();
            return  parseJson(resJsonString, gson);
        } else {//响应失败
            return null;
        }
    }
    //1.1 保存数据到本地
    private void writeData2Local(String resJsonString, int index) {
        File cacheFile = getCacheFile(index);
        LogUtils.s("###缓存数据到本地-->"+cacheFile.getAbsolutePath());
        BufferedWriter writer = null;
        try{
            //得到缓存的file
            writer = new BufferedWriter(new FileWriter(cacheFile));
            //写入第一行，缓存的生成时间
            writer.write(System.currentTimeMillis()+"");
            //换行--->细节地方
            writer.newLine();
            //写入第二行，缓存真是内容
            writer.write(resJsonString);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }
    //1.2 参数部分（子类选择性重写）
    public Map<String, Object> getParamsMap(int index) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("index", index + "");
        return paramsMap;
    }
    //1.3 解析json数据交给子类去实现
    public abstract T parseJson(String resJsonString, Gson gson);

    //2.从本地加载数据
    private T loadDataFromLocal(int index) {
        BufferedReader reader = null;
    try{
        File cacheFile = getCacheFile(index);
        if(cacheFile.exists()){ //可能有效的缓存文件
            //所以先读取第一行，判断缓存时间是否有效
            reader = new BufferedReader(new FileReader(cacheFile));
            String firstLine = reader.readLine();
            long cacheInsertTime = Long.parseLong(firstLine);
            if((System.currentTimeMillis()-cacheInsertTime)<PROTOCOLTIMETOU){
                //读取第二行，得到缓存的内容
                String cacheJsonString = reader.readLine();
                //保存数据到内存
                writeData2Men(cacheJsonString,index);
                //解析返回
                return parseJson(cacheJsonString,new Gson());
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            IOUtils.close(reader);
        }
            return  null;
    }
    //2.1 创建生成缓存的唯一Key的方法
    public String generateKey(int index){
        return getInterfaceKey() + "." + index;
    }
    //2.1 得到协议的关键字
    public abstract String getInterfaceKey();

    //2.2 得到缓存文件
    private File getCacheFile(int index) {
        //生成唯一的key
        String key = generateKey(index);
        //初始化缓存文件
        String dir = FileUtils.getDir("json");
        String fileName = key;
        return new File(dir,fileName);
    }

     //2.3 保存数据到内存
    private void writeData2Men(String cacheJsonString, int index) {
        //得到存储结构
        MyApplication app = (MyApplication) UIUtils.getContext();
        Map<String, String> cacheJsonMap = app.mCacheJsonMap;
        String key = generateKey(index);
        cacheJsonMap.put(key,cacheJsonString);
    }
    //3.从内存，返回数据
    private T loadDataFromMem(int index) {
        //得到存储结构
        MyApplication app = (MyApplication) UIUtils.getContext();
        Map<String, String> cacheJsonMap = app.mCacheJsonMap;
        //得到缓存的key
        String key = generateKey(index);
        //判断是否存储缓存
        if(cacheJsonMap.containsKey(key)){
            String cacheMemJsonString = cacheJsonMap.get(key);
            //解析返回
            return parseJson(cacheMemJsonString, new Gson());
        }
        return null;
    }

}
