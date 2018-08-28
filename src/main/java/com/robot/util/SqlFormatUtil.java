package com.robot.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.Keywords;

public class SqlFormatUtil {
    static Keywords KWS = Keywords.SQLITE_KEYWORDS;
    public static String format(String text) {
        String formatHive = SQLUtils.formatHive(text);
        formatHive = formatHive.replaceAll("\n", "  \n > ");
        formatHive = formatHive.replaceAll("=", getOrange("="));
        formatHive = formatHive.replaceAll("([\\s\\(]|^)(" + getKws() + ")([\\s])", "$1" + getRed("$2") + "$3");
        formatHive = formatHive.replaceAll("(\\.)(\\w+)(\\n|\\(| |,|$)", "$1" + getBlue("$2")  + "$3");
        formatHive = formatHive.replaceAll("( )(\\w+)(\\()", "$1" + getBlue("$2")  + "$3");
        formatHive = formatHive.replaceAll("(\\s)('|\")(\\S+)('|\")(\\s|$)", "$1" + getBlue("$2") + getYellow("$3") + getBlue("$4") + "$5");
        formatHive = formatHive.replaceAll("([\\s\\(]-{0,1})(\\d+)([\\s\\)]|$)", "$1" + getGreen("$2")  + "$3");
        formatHive = formatHive.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        formatHive = formatHive.replaceAll("_", "\\\\_");
        formatHive = formatHive.replaceAll("\\*", "\\\\\\\\*");
        return " > " + formatHive;
    }
    public static void main(String[] args) {
        System.out.println(format("select * from user where name = '1'"));
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
    
    private static String getYellow(String string) {
        return "<font color=#FFEC8B >" + string + "</font>";
    }
    
    private static String getOrange(String string) {
        return "<font color=#FFA500 >" + string + "</font>";
    }
}
