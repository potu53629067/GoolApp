package com.peng.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peng.base.BaseProtocol;
import com.peng.bean.SubjectInfo;

import java.util.List;

/**
 * Created by peng on 2017/11/12.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {
    @Override
    public String getInterfaceKey() {
        return "subject";
    }

    @Override
    public List<SubjectInfo> parseJson(String resJsonString, Gson gson) {
        return gson.fromJson(resJsonString,new TypeToken<List<SubjectInfo>>(){}.getType());
    }
}
