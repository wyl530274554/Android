package com.melon.app;

import com.melon.mylibrary.CustomApplication;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.SpUtil;

/**
 * 接口地址管理
 *
 * @author melon.wang
 * 2019/1/7 17:28
 */
public class ApiManager {
    private static final String API_PROTOCOL = "http://";
    private static final String API_IP = "192.168.100.234";
    private static final String API_PORT = "80";
    private static String API_BASE = getApiBase() + "/";


    public static final String API_NOTE_ADD = API_BASE + "note/addNote";
    public static final String API_NOTE_DEL = API_BASE + "note/delNote";
    public static final String API_NOTE_UPDATE = API_BASE + "note/updateNote";
    public static final String API_NOTE_QUERY = API_BASE + "note/queryNote";
    public static final String API_NOTE_ALL = API_BASE + "note/queryAllNote";
    public static final String API_GET_ALL_IMAGE = API_BASE + "index/allImages";

    public static final String API_CONTACTS = API_BASE + "contacts/";
    public static final String API_PASSWORD = API_BASE + "password";
    public static final String API_FOO = API_BASE;
    public static final String API_APP_UPGRADE = API_BASE + "/upgrade";

    public static final String APP_DOWNLOAD = API_BASE.replace(":80", ":2020") + "/";

    private static String getApiBase() {
        String host = SpUtil.getString(CustomApplication.getInstance(), "api_host");
        if (CommonUtil.isEmpty(host)) {
            return API_PROTOCOL + API_IP + ":" + API_PORT;
        } else {
            return API_PROTOCOL + host;
        }
    }

    public static String getApiHost() {
        String host = SpUtil.getString(CustomApplication.getInstance(), "api_host");
        if (CommonUtil.isEmpty(host)) {
            return API_IP + ":" + API_PORT;
        } else {
            return host;
        }
    }

    public static String getApiIp() {
        String host = SpUtil.getString(CustomApplication.getInstance(), "api_host");
        if (!CommonUtil.isEmpty(host)) {
            String[] split = host.split(":");
            return split[0];
        }
        return API_IP;
    }

    public static String getApiPort() {
        String host = SpUtil.getString(CustomApplication.getInstance(), "api_host");
        if (!CommonUtil.isEmpty(host)) {
            String[] split = host.split(":");
            return split[1];
        }
        return API_PORT;
    }
}
