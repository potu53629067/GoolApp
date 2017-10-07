package com.peng.utils;

import java.util.Map;


/**
 * Created by peng on 2017/9/16.
 */
public class HttpUtils {
    public static String getUrlParamsByMap(Map<String,Object> map){
        if(map == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String,Object> entry:map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if(s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return  s;
        }

}
