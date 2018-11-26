package com.robot.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.robot.bean.repository.SubscriberRepository;
import com.robot.entity.ActionCard;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.Btn;
import com.robot.entity.MarkDown;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatbotSender {
    
    @Value("${robot.meal-team.token}")
    private String mealTeamToken;
    
    @Autowired
    private SubscriberRepository subscriberRepository;
    
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
    
    public void sendMealMessageMO() throws Exception {
        List<String> users = getUsers();
        Lists.partition(users, 5).forEach(userList -> {
            String text = "## 加班餐提醒\n" + 
                    "-----\n" + 
                    "现在的时间是<font color=#CC3300 >9:29</font>，距离今天可以点加班餐的开始时间还剩<font color=#CC3300 >1</font>分钟。  \n" + 
                    "&nbsp;  " + 
                    "\n" + 
                    "<font color=#3333ff >@" + StringUtils.join(userList, " @") + "</font>";
            try {
                new ChatbotSender().send(Body.builder()
                        .actionCard(ActionCard.builder()
                                .btns(Arrays.asList(new Btn("直达加班餐", "https://entu.rajax.me"),
                                        new Btn("♈♈♈♈♈♈♈", "https://entu.rajax.me"),
                                        new Btn("订阅", "dtmd://dingtalkclient/sendMessage?content=订阅加班餐"),
                                        new Btn("取消订阅", "dtmd://dingtalkclient/sendMessage?content=取消订阅加班餐")))
                                .title("加班餐提醒")
                                .text(text)
                                .build())
                        .at(At
                                .builder()
                                .atDingtalkIds(userList)
                                .build())
                        .msgtype("actionCard")
                        .build(), ChatbotSender.WEBHOOK_TOKEN_PRE + mealTeamToken);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        });
    }
    
    public void sendMealMessageAF() throws Exception {
        List<String> users = getUsers();
        Lists.partition(users, 5).forEach(userList -> {
            String text = "## 加班餐提醒\n" + 
                    "-----\n" + 
                    "现在的时间是<font color=#CC3300 >14:55</font>，距离今天可以点加班餐的截止时间还剩<font color=#CC3300 >5</font>分钟。  \n" + 
                    "&nbsp;  " + 
                    "\n" + 
                    "<font color=#3333ff >@" + StringUtils.join(userList, " @") + "</font>";
            try {
                new ChatbotSender().send(Body.builder()
                        .actionCard(ActionCard.builder()
                                .btns(Arrays.asList(new Btn("直达加班餐", "https://entu.rajax.me"),
                                        new Btn("♈♈♈♈♈♈♈", "https://entu.rajax.me"),
                                        new Btn("订阅", "dtmd://dingtalkclient/sendMessage?content=订阅加班餐"),
                                        new Btn("取消订阅", "dtmd://dingtalkclient/sendMessage?content=取消订阅加班餐")))
                                .title("加班餐提醒")
                                .text(text)
                                .build())
                        .at(At
                                .builder()
                                .atDingtalkIds(userList)
                                .build())
                        .msgtype("actionCard")
                        .build(), ChatbotSender.WEBHOOK_TOKEN_PRE + mealTeamToken);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        });
    }
    
    private List<String> getUsers() {
        Set<String> memberSet = new HashSet<>();
        subscriberRepository.findAll().forEach(s -> {
            memberSet.add(s.getUserId());
        });
        return new ArrayList<>(memberSet);
    }
}
