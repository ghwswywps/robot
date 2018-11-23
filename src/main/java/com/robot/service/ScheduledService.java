package com.robot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.robot.helper.ChatbotSender;

import lombok.extern.log4j.Log4j;

/**
 * 加班餐提醒定时
 */
@Component
@Log4j
public class ScheduledService {
    @Autowired
    private ChatbotSender chatbotSender;
    
    @Scheduled(cron = "0 29 9 ? * MON-FRI")
    public void mo(){
        try {
            chatbotSender.sendMealMessageMO();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    

    @Scheduled(cron = "0 55 14 ? * MON-FRI")
    public void af(){
        try {
            chatbotSender.sendMealMessageAF();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
