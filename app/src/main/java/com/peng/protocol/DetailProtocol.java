package com.peng.protocol;

import com.google.gson.Gson;
import com.peng.base.BaseProtocol;
import com.peng.bean.DetailBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng on 2017/11/13.
 */

public class DetailProtocol extends BaseProtocol<DetailBean> {
    //4.packageName通过构造方法拿
    public String mPackageName;
    public DetailProtocol(String packageName){
        this.mPackageName = packageName;
    }
    //1.请求参数的key
    @Override
    public String getInterfaceKey() {
        return "detail";
    }
    //2.解析json数据
    @Override
    public DetailBean parseJson(String resJsonString, Gson gson) {
        return gson.fromJson(resJsonString,DetailBean.class);
    }
    //3.重写请求参数拼接
    @Override
    public Map<String, Object> getParamsMap(int index) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("packageName",mPackageName);
        return paramsMap;
    }
}
