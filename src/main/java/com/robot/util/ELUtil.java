package com.robot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.robot.entity.ELEnum;
import com.robot.entity.ELNode;

public class ELUtil {
    public static boolean check(String context, ELNode node) {
        boolean result = true;
        if (node.getElEnum().equals(ELEnum.string)) {
            if(node.getValue().startsWith("*"))
                return context.trim().equals(node.getValue().trim().split("\\*")[1]);
            else
                return context.contains(node.getValue());
        }
        List<ELNode> nodes = node.getNodes();
        ELNode firstNode = nodes.get(0);
        result = check(context, firstNode);
        for (int i = 0; i < nodes.size() - 2; i += 2) {
            ELNode symNode = nodes.get(i + 1);
            ELNode calNode = nodes.get(i + 2);
            switch (symNode.getElEnum()) {
            case and:
                result &= check(context, calNode);
                break;
            case or:
                result |= check(context, calNode);
                break;
            default:
                break;
            }
        }

        return result;
    }

    public static ELNode getNode(String el) {
        ELNode node = new ELNode();
        node.setElEnum(ELEnum.list);
        node.setNodes(new ArrayList<>());
        List<ELNode> nodes = node.getNodes();
        char[] charArray = el.toCharArray();
        StringBuffer cacheSB = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            if (i > 0 && Arrays.asList('|', '&', '(', ')').contains(charArray[i])) {
                String last = new String(cacheSB);
                if (!StringUtils.isEmpty(last)) {
                    ELNode string = new ELNode();
                    string.setElEnum(ELEnum.string);
                    string.setValue(new String(last));
                    nodes.add(string);
                    cacheSB = new StringBuffer();
                }
            }
            if (charArray[i] == '&') {
                nodes.add(ELNode.AND);
            } else if (charArray[i] == '|') {
                nodes.add(ELNode.OR);
            } else if (charArray[i] == '(') {
                char[] rest = new char[charArray.length - i - 1];
                System.arraycopy(charArray, i + 1, rest, 0, charArray.length - i - 1);
                char[] deepChar = getDeepChar(rest);
                i += deepChar.length + 1;
                nodes.add(getNode(new String(deepChar)));
            } else {
                cacheSB.append(charArray[i]);
            }
        }
        String last = new String(cacheSB);
        if (!StringUtils.isEmpty(last)) {
            ELNode string = new ELNode();
            string.setElEnum(ELEnum.string);
            string.setValue(new String(last));
            nodes.add(string);
        }
        return node;
    }

    private static char[] getDeepChar(char[] rest) {
        int deep = -1;
        for (int i = 0; i < rest.length; i++) {
            if (rest[i] == ')') {
                deep++;
            } else if (rest[i] == '(') {
                deep--;
            }
            if (deep >= 0) {
                char[] reslut = new char[i];
                System.arraycopy(rest, 0, reslut, 0, i);
                return reslut;
            }
        }
        return null;
    }
}
