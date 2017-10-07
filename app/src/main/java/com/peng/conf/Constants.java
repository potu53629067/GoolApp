package com.peng.conf;

import com.peng.utils.LogUtils;

/**
 * Created by peng on 2017/9/12.
 */
public class Constants {


     public static final class URL{
        //public static final  String BASEURL = "http://10.0.3.2:8080/GooglePlayServer/";
        //public static final  String BASEURL = "http://localhost:8080/GooglePlayServer/";
        //public static final  String BASEURL = "http://10.0.2.2:8080/GooglePlayServer/";
         //192.168.1.108
        public static final  String BASEURL = "http://192.168.1.108:8080/GooglePlayServer/";
        //public static final  String BASEURL = "http://com.example.proxy:8080/GooglePlayServer/";
        public static final  String IMAGBURL = BASEURL + "image?name=";
    }





    /*
        LogUtils.LEVEL_ALL:显示所有日志
         LogUtils.LEVEL_OFF:屏蔽所有日志
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;


    public static final class REQ {

    }

    public static final class RES {

    }

    public static final class PAY {
        public static final int PAYTYPE_ZHIFUBAO =0 ;
        public static final int PAYTYPE_UUPAY    = 1;
        public static final int PAYTYPE_WEIXIN   = 2;
    }


}
