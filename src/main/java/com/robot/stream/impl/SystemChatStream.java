package com.robot.stream.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.entity.Body;
import com.robot.entity.Order;
import com.robot.entity.Order.Power;
import com.robot.entity.Request;
import com.robot.entity.User;
import com.robot.handler.OrderHandler;
import com.robot.helper.PowerHelper;
import com.robot.stream.ChatStream;
import com.robot.stream.StaticText;

@Component
public class SystemChatStream implements ChatStream{
    @Autowired
    private PowerHelper powerHelper;

    @Override
    public int weight() {
        return StaticText.WEIGHT_SYSTEM;
    }

    @Override
    public Body handle(String order, Request request) throws Exception {
        String content = request.getText().getContent();
        String senderId = request.getSenderId();
        Power power = powerHelper.getPowerByUserId(senderId);
        List<User> atUsers = request.getAtUsers();
        String chatbotUserId = request.getChatbotUserId();
        atUsers.removeIf(user -> user.getDingtalkId().equals(chatbotUserId));
        
        Map<String, Order> orderMap = OrderHandler.orderMap;
        for (String key : orderMap.keySet()) {
            if (content.trim().startsWith(key)) {
                Order orderSystem = orderMap.get(key);
                Power orderPower = orderSystem.getPower();
                if(orderPower.getId() < power.getId())
                    break;
                Map<String, String> property = getProperty(content);
                property.put("atUsers", JSON.toJSONString(atUsers));
                checkArgs(orderSystem.getArgs(), property);
                return orderSystem.getAction().get(property);
            }
        }
        return StaticText.EMPTY_BODY;

    }
    
    private Map<String, String> getProperty(String content) throws Exception {
        Pattern pattern = Pattern.compile("\"\"\"([\\s\\S]*?)\"\"\"");
        Matcher matcher = pattern.matcher(content);
        Map<String, String> valueTable = new HashMap<>();
        int t = 0;
        while (matcher.find()) {
            String v = matcher.group(1);
            String tableName = "{__tableName__" + t + "}";
            valueTable.put(tableName, v);
            content = matcher.replaceFirst(tableName);
            matcher = pattern.matcher(content);
            t++;
        }
        Map<String, String> p = new HashMap<>();
        String[] split = content.trim().split("[\\s\t\n￥]+");
        for (int i = 1; i < split.length; i++) {
            String v = split[i];
            if (!v.contains(":::"))
                throw new Exception("参数非法:" + v);
            String[] kv = v.split(":::");
            if (valueTable.get(kv[1]) == null)
                p.put(kv[0], kv[1]);
            else
                p.put(kv[0], valueTable.get(kv[1]));
        }
        return p;
    }
    
    private void checkArgs(List<String> args, Map<String, String> property) throws Exception {
        List<String> needArgs = args.stream().filter(arg -> arg.startsWith("\\*")).collect(Collectors.toList());
        for (String p : needArgs) {
            if (property.get(p.split("\\*")[1]) == null)
                throw new Exception("参数非法，请检查必要参数！");
        }
    }
}
