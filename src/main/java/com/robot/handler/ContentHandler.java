package com.robot.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.MDTemple;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.entity.TuLingBody;
import com.robot.util.ELUtil;

@Component
public class ContentHandler {
    @Autowired
    private TempleRepository templeRepository;

    public Body getBodyByRequest(Request request) {
        Body body = new Body();
        try {
            String content = request.getText().getContent();
            handleBody(body, content);
        } catch (Exception e) {
            body.setMsgtype("text");
            body.setText(Text.builder().content(e.getMessage()).build());
        }
        body.setAt(At.builder().atDingtalkIds(Arrays.asList(request.getSenderId())).build());
        return body;
    }

    private void handleBody(Body body, String content) throws Exception {
        if (content.trim().startsWith("增加模板")) {
            String[] split = content.trim().split(" ");
            String el = split[1];
            String msgtype = split[2];
            String temple = split[3];
            Temple t = new Temple();
            t.setEl(el);
            t.setMsgtype(msgtype);
            t.setTemple(temple);
            templeRepository.save(t);
            body.setMsgtype("text");
            body.setText(Text.builder().content("添加成功").build());
            return;
        }
        Iterable<Temple> all = templeRepository.findAll();
        List<MDTemple> mdts = new ArrayList<>();
        all.forEach(t -> {
            MDTemple mdt = new MDTemple();
            mdt.setEl(t.getEl());
            mdt.setTemple(t.getTemple());
            mdt.setMsgtype(t.getMsgtype());
            mdt.setElNode(ELUtil.getNode(t.getEl()));
            mdts.add(mdt);
        });
        for (MDTemple mdTemple : mdts) {
            if (ELUtil.check(content, mdTemple.getElNode())) {
                body.setMsgtype(mdTemple.getMsgtype());
                body.setText(Text.builder().content(mdTemple.getTemple()).build());
                return;
            }
        }
        body.setMsgtype("text");
        body.setText(Text.builder().content(tuling(content)).build());
    }

    public String tuling(String context) throws Exception {
        String APIKEY = "402536689fcf4282ae1f213e70c6a819";
        String url = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info="
                + URLEncoder.encode(context.trim(), "utf-8");
        System.out.println(url);
        StringBuffer sb = null;
        // 取得输入流，并使用Reader读取
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), "utf-8"))) {
            sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb == null ? "啥都没返回！" : JSON.parseObject(new String(sb), TuLingBody.class).getText();
    }

}
