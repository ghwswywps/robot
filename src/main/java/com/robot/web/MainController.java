package com.robot.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.robot.entity.Body;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.handler.ContentHandler;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class MainController {
    @Autowired
    private ContentHandler contentHandler;

    public static List<Long> teachersMobiles = new ArrayList<>();
    
    @Value("${robot.self.token}")
    private String selfToken;
    
    /**
     * 钉钉回调接口（官网规范）
     * @param requestStr
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    String home(@RequestBody String requestStr, @RequestHeader(value="token") String token) throws Exception {
        //简单鉴权
        if (!StringUtils.isEmpty(selfToken)) {
            if (!selfToken.equals(token)) {
                log.info("token:" + token);
                return "通关密码好像不对吧..😀";
            }
        }

        log.info(requestStr);
        Request request = JSON.parseObject(requestStr, Request.class);
        Body body = contentHandler.getBodyByRequest(request);
        String res = JSON.toJSONString(body);
        log.info(res);
        return res;
    }
    /**
     * 测试接口（postman会有字符问题）
     * @param content
     * @return
     */
    @RequestMapping(value = "/test/{content}", produces = "application/json; charset=utf-8")
    @ResponseBody
    String test(@PathVariable("content") String content) {
        Request r = new Request();
        r.setText(new Text());
        r.getText().setContent(content);
        r.setAtUsers(new ArrayList<>());
        r.setSenderId("1");
        Body body = contentHandler.getBodyByRequest(r);
        return JSON.toJSONString(body);
    }


    /**
     * 获取老师数据
     * @param content
     * @return
     */
    @RequestMapping(value = "/getTeacherMobiles", produces = "application/json; charset=utf-8")
    @ResponseBody
    String getTeacherMobiles() {
        ArrayList<Long> arrayList = new ArrayList<>(teachersMobiles);
        teachersMobiles = new ArrayList<>();
        return JSON.toJSONString(arrayList);
    }
}
