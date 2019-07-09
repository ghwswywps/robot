package com.robot.util;

import com.overzealous.remark.Remark;
import com.robot.entity.md.MarkDown2HtmlWrapper;

public class ConvertUtil {
    public static String convertFromMD2Html(String content) {
        return MarkDown2HtmlWrapper.ofContent(content).toString();
    }
    
    public static String convertFromHtml2MD(String content) {
        Remark remark = new Remark();
        return remark.convert(content);
    }
}
