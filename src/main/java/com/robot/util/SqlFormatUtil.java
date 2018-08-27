package com.robot.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.Keywords;

public class SqlFormatUtil {
    static Keywords KWS = Keywords.SQLITE_KEYWORDS;
    public static String format(String text) {
        String formatHive = SQLUtils.formatHive(text);
        formatHive = formatHive.replaceAll("\n", "  \n > ");
        formatHive = formatHive.replaceAll("([\\s\t\n\\(]|^)(" + getKws() + ")([\\s\t\n])", "$1" + getRed("$2") + "$3");
        formatHive = formatHive.replaceAll("(\\.)(\\w+)(\\n|\\(| |,|$)", "$1" + getBlue("$2")  + "$3");
        formatHive = formatHive.replaceAll("( )(\\w+)(\\()", "$1" + getBlue("$2")  + "$3");
        formatHive = formatHive.replaceAll("([\\s\t\n\\(]-{0,1})(\\d+)([\\s\t\n\\)])", "$1" + getGreen("$2")  + "$3");
        formatHive = formatHive.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        formatHive = formatHive.replaceAll("_", "\\\\_");
        formatHive = formatHive.replaceAll("\\*", "\\\\\\\\*");
        return " > " + formatHive;
    }

    private static String getGreen(String string) {
        return "<font color=#00cc00 >" + string + "</font>";
    }

    private static String getKws() {
        return StringUtils.join(KWS.getKeywords().keySet(), "|");
    }

    private static String getRed(String string) {
        return "<font color=#CC3300 >" + string + "</font>";
    }
    
    private static String getBlue(String string) {
        return "<font color=#3333ff >" + string + "</font>";
    }
}
