package com.robot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robot.helper.DingSender;

/**
 * 加班餐提醒任务
 */
@Component
public class ScheduledService {
    @Autowired
    private DingSender chatbotSender;
    
}
