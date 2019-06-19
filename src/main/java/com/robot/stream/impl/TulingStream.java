package com.robot.stream.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.robot.entity.Body;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.entity.tl.InputText;
import com.robot.entity.tl.Perception;
import com.robot.entity.tl.TLBody;
import com.robot.entity.tl.UserInfo;
import com.robot.helper.TulingSender;
import com.robot.stream.ChatStream;
import com.robot.stream.StaticText;

@Component
public class TulingStream implements ChatStream{
    
    @Value("${robot.tuling.apikey}")
    private String tulingApikey;
    
    @Autowired
    private TulingSender tulingSender;
    
    @Override
    public int weight() {
        return StaticText.WEIGHT_TLAPI;
    }

    @Override
    public Body handle(String order, Request request) throws Exception {
        String content = request.getText().getContent();
        String senderId = request.getSenderId();
        Body body = new Body();
        body.setMsgtype("text");
        body.setText(Text.builder().content(tuling(content, senderId)).build());
        return body;
    }
    
    public String tuling(String context, String userId) throws Exception {
        TLBody body = new TLBody();
        Perception perception = new Perception();
        body.setPerception(perception);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setApiKey(tulingApikey);
        perception.setInputText(new InputText(context));
        body.setUserInfo(userInfo);
        return tulingSender.send(body);
    }
    
}
