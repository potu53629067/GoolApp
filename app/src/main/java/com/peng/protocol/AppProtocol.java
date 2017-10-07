package com.peng.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peng.base.BaseProtocol;
import com.peng.bean.ItemBean;

import java.util.List;

/**
 * Created by peng on 2017/10/3.
 */
public class AppProtocol extends BaseProtocol<List<ItemBean>> {
    @Override
    public String getInterfaceKey() {
        return "app";
    }

    @Override
    public List<ItemBean> parseJson(String resJsonString, Gson gson) {

        return gson.fromJson(resJsonString,new TypeToken<List<ItemBean>>(){}.getType());
    }
}
