package com.robot.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.robot.entity.ActionCard;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.Btn;
import com.robot.entity.MarkDown;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatbotSender {
    
    public static String WEBHOOK_TOKEN_PRE = "https://oapi.dingtalk.com/robot/send?access_token=";

    public void send(Body body, String token) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(token);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(JSON.toJSONString(body), "utf-8");
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
    
    public static void main1(String[] args) throws Exception {
        String littleTeamToken = "13106d51c8a7cda15da07d402cd1db1103895e6a77f94c49ffca25fb87c0485c";
        String text = "## 加班餐提醒\n" + 
                "-----\n" + 
                "现在的时间是<font color=#CC3300 >9:29</font>，距离今天可以点加班餐的开始时间还剩<font color=#CC3300 >1</font>分钟。  \n" + 
                "> 快捷通道：  \n" + 
                "> [![加班餐](http://localhost:10924/images/tomato.png)](https://entu.rajax.me)  \n" +
                "&nbsp;  " + 
                "\n" + 
                "<font color=#3333ff >@" + getAtString() + "</font>";
        new ChatbotSender().send(Body.builder()
                .actionCard(ActionCard.builder()
                        .btns(Arrays.asList(new Btn("订阅", "dtmd://dingtalkclient/sendMessage?content=订阅"),
                                new Btn("取消订阅", "dtmd://dingtalkclient/sendMessage?content=取消订阅")))
                        .title("加班餐提醒")
                        .text(text)
                        .build())
                .at(At
                        .builder()
                        .atMobiles(Arrays.asList("17321044687"))
                        .build())
                .msgtype("actionCard")
                .build(), ChatbotSender.WEBHOOK_TOKEN_PRE +littleTeamToken);
        
    }
    
    private static String getAtString() {
        String littleTeamMembers = "[17321044687]";
        return StringUtils.join(JSON.parseObject(littleTeamMembers, new TypeReference<ArrayList<String>>() {}), " @");
    }
}
