package com.robot.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import lombok.Builder;
import lombok.Data;

@Controller
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    String home(@RequestBody String request) {
        Body body = new Body();
        body.setMsgtype("text");
        body.setText(Text.builder().content(request).build());
        body.setAt(At.builder().atMobiles(Arrays.asList("18856710282")).build());
        return JSON.toJSONString(body);
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
    public static class At {
        private List<String> atMobiles;
        private List<String> atDingtalkIds;
    }
}
