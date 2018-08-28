package com.robot.util;

public class DingUtil {
    public static String getSendingLinkInMD(String order) {
        return "[" + order + "](dtmd://dingtalkclient/sendMessage?content=" + order + ")";
    }
    
    public static String getSendingLinkInMD(String show, String order) {
        return "[" + show + "](dtmd://dingtalkclient/sendMessage?content=" + order + ")";
    }
}
