package com.robot.entity;

import java.util.List;

import lombok.Data;
@Data
public class Request {
    private String msgtype;
    private Text text;
    private Long createAt;
    private String conversationType;
    private String conversationId;
    private String conversationTitle;
    private String senderId;
    private String senderNick;
    private String senderStaffId;
    private boolean isAdmin;
    private String context;
    private String chatbotCorpId;
    private String chatbotUserId;
    private List<User> atUsers;
}
