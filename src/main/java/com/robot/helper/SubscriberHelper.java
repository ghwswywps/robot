package com.robot.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.robot.bean.Subscriber;
import com.robot.bean.repository.SubscriberRepository;
import com.robot.entity.Body;
import com.robot.entity.Request;
import com.robot.entity.Text;

@Component
public class SubscriberHelper {
    @Autowired
    private SubscriberRepository subscriberRepository;
    public Body subscribe(Request request) {
        Body body = new Body();
        body.setMsgtype("text");
        String text = null;
        String senderId = request.getSenderId();
        List<Subscriber> userIds = subscriberRepository.findByUserId(senderId);
        if (CollectionUtils.isEmpty(userIds)) {
            text += "已订阅加班餐提醒，请勿重复订阅！";
        } else {
            subscriberRepository.save(new Subscriber(0, senderId));
            text += "订阅成功！";
        }
        body.setText(Text.builder().content(text).build());
        return body;
    }

    public Body unSubscribe(Request request) {
        Body body = new Body();
        body.setMsgtype("text");
        String text = null;
        String senderId = request.getSenderId();
        List<Subscriber> userIds = subscriberRepository.findByUserId(senderId);
        if (!CollectionUtils.isEmpty(userIds)) {
            text += "还未订阅加班餐提醒，无法取消！";
        } else {
            subscriberRepository.delete(userIds);
            text += "取消成功！";
        }
        body.setText(Text.builder().content(text).build());
        return body;
    }
}
