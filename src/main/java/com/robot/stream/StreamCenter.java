package com.robot.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StreamCenter implements ApplicationContextAware{
    private static ApplicationContext applicationContext;
    public static List<ChatStream> getStream() {
        Map<String, ChatStream> beansOfType = applicationContext.getBeansOfType(ChatStream.class);
        ArrayList<ChatStream> result = new ArrayList<>(beansOfType.values());
        result.sort((a, b) -> a.weight() - b.weight());
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StreamCenter.applicationContext == null) {
            StreamCenter.applicationContext = applicationContext;
        }
    }
}
