package com.robot.helper;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.MarkDown;

import lombok.extern.slf4j.Slf4j;

/**
 * incoming交互方式的一些方法
 */
@Slf4j
@Component
public class DingSender {
    
    @Value("${robot.meal-team.token}")
    private String mealTeamToken;
    
    
    public static String WEBHOOK_TOKEN_PRE = "https://oapi.dingtalk.com/robot/send?access_token=";

    public void send(Body body, String token) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(token);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(JSON.toJSONString(body), "utf-8");
        log.info("SenderBody:" + JSON.toJSONString(body));
        httppost.setEntity(se);
        httpclient.execute(httppost);
    }
    
    public void sendMarkdown(String title, String text,String token, List<String> atPhone) {
        try {
            send(Body.builder()
                    .markdown(MarkDown
                            .builder()
                            .text(text)
                            .title(title)
                            .build())
                    .at(At
                            .builder()
                            .atMobiles(atPhone)
                            .build())
                    .msgtype("markdown")
                    .build(), token);
        } catch (Exception e) {
            log.error("robot异常:" + e);
        }
    }
}
