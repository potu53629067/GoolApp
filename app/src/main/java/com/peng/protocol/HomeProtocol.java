package com.peng.protocol;



import com.google.gson.Gson;
import com.peng.base.BaseProtocol;
import com.peng.bean.HomeBean;

/**
 * Created by peng on 2017/9/16.
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    public String getInterfaceKey() {
        return "home";
    }

    @Override
    public HomeBean parseJson(String resJsonString, Gson gson) {
        HomeBean homeBean = gson.fromJson(resJsonString,HomeBean.class);
        return homeBean;
    }
}

