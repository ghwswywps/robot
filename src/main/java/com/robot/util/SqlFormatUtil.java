package com.robot.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.Keywords;

public class SqlFormatUtil {
    static Keywords KWS = Keywords.SQLITE_KEYWORDS;

    public static String format(String text) {
        String formatHive = SQLUtils.formatHive(text);
        formatHive = formatHive.replaceAll("\n", "  \n > ");
        formatHive = formatHive.replaceAll("=", ColorUtil.getOrange("="));
        formatHive = formatHive.replaceAll("([\\s\\(]|^)(" + getKws() + ")([\\s])",
                "$1" + ColorUtil.getRed("$2") + "$3");
        formatHive = formatHive.replaceAll("(\\.)(\\w+)(\\n|\\(| |,|$)", "$1" + ColorUtil.getBlue("$2") + "$3");
        formatHive = formatHive.replaceAll("( )(\\w+)(\\()", "$1" + ColorUtil.getBlue("$2") + "$3");
        formatHive = formatHive.replaceAll("(\\s)('|\")(\\S+)('|\")(\\s|$)",
                "$1" + ColorUtil.getBlue("$2") + ColorUtil.getYellow("$3") + ColorUtil.getBlue("$4") + "$5");
        formatHive = formatHive.replaceAll("([\\s\\(]-{0,1})(\\d+)([\\s\\)]|$)",
                "$1" + ColorUtil.getGreen("$2") + "$3");
        formatHive = formatHive.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        formatHive = formatHive.replaceAll("_", "\\\\_");
        formatHive = formatHive.replaceAll("\\*", "\\\\\\\\*");
        return " > " + formatHive;
    }

    private static String getKws() {
        return StringUtils.join(KWS.getKeywords().keySet(), "|");
    }

}
