package com.peng.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peng.base.BaseProtocol;

import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    public String getInterfaceKey() {
        return "recommend";
    }

    @Override
    public List<String> parseJson(String resJsonString, Gson gson) {
        return gson.fromJson(resJsonString,new TypeToken<List<String>>(){}.getType());
    }
}
