package com.robot.service;


import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.robot.helper.ChatbotSender;

/**
 * 加班餐提醒定时
 */
@Component
public class ScheduledService {
    @Value("${robot.little-team.token}")
    private String littleTeamToken;
    
    @Value("${robot.little-team.members}")
    private String littleTeamMembers;
    
    @Autowired
    private ChatbotSender chatbotSender;
    
    @Scheduled(cron = "0 29 9 * * ?")
    public void mo(){
        if(littleTeamToken != null) {
            chatbotSender.sendMarkdown("加班餐提醒",
                    "## 加班餐提醒\n" + 
                    "-----\n" + 
                    "现在的时间是<font color=#CC3300 >9:29</font>，距离今天可以点加班餐的开始时间还剩<font color=#CC3300 >1</font>分钟。  \n" + 
                    "\n" + 
                    "<font color=#3333ff >@" + getAtString() + "</font>", ChatbotSender.WEBHOOK_TOKEN_PRE +littleTeamToken, 
                    JSON.parseObject(littleTeamMembers, new TypeReference<ArrayList<String>>() {}));
        }
    }
    

    @Scheduled(cron = "0 55 14 * * ?")
    public void af(){
        if(littleTeamToken != null) {
            chatbotSender.sendMarkdown("加班餐提醒",
                    "## 加班餐提醒\n" + 
                     "-----\n" + 
                     "现在的时间是<font color=#CC3300 >14:55</font>，距离今天可以点加班餐的截止时间还剩<font color=#CC3300 >5</font>分钟。  \n" + 
                     "\n" + 
                     "<font color=#3333ff >@" + getAtString() + "</font>", ChatbotSender.WEBHOOK_TOKEN_PRE +littleTeamToken, 
                     JSON.parseObject(littleTeamMembers, new TypeReference<ArrayList<String>>() {}));
        }
    }
    
    private String getAtString() {
        return StringUtils.join(JSON.parseObject(littleTeamMembers, new TypeReference<ArrayList<String>>() {}), " @");
    }
    
}
