package com.robot.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Entity {
    @Data
    public static class Request {
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

    @Data
    public static class User {
        String dingtalkId;
    }

    @Data
    public static class Body {
        private String msgtype;
        private Text text;
        private At at;
        private boolean isAtAll;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text {
        private String content;
    }

    @Data
    public static class Image {
        private String picURL;
    }

    @Data
    public static class MarkDown {
        private String title;
        private String text;
    }

    @Data
    public static class ActionCard {
        private String title;
        private String text;
        private String hideAvatar;
        private String btnOrientation;
        private String singleTitle;
        private String singleURL;
        private List<Btn> btns;
    }

    @Data
    public static class Btn {
        private String title;
        private String actionURL;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class At {
        private List<String> atMobiles;
        private List<String> atDingtalkIds;
    }

    @Data
    public static class TuLingBody {
        private Long code;
        private String text;
    }
    
    @Data
    public static class MDTemple {
        private String el;
        private String temple;
        private String msgtype;
        private ELNode elNode;
    }
    
    @Data
    public static class ELNode {
        private List<ELNode> nodes;
        private ELEnum elEnum;
        private String value;
        public static final ELNode OR = new ELNode();
        public static final ELNode AND = new ELNode();
        static {
            OR.setElEnum(ELEnum.or);
            AND.setElEnum(ELEnum.and);
        }
    }
    
    public static enum ELEnum {
        or,and,string,list
    }
}
