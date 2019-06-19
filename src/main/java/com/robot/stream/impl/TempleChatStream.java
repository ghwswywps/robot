package com.robot.stream.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.Body;
import com.robot.entity.Link;
import com.robot.entity.MDTemple;
import com.robot.entity.MarkDown;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.stream.ChatStream;
import com.robot.stream.StaticText;
import com.robot.util.ELUtil;

@Component
public class TempleChatStream implements ChatStream{
    @Autowired
    private TempleRepository templeRepository;
    
    public static List<MDTemple> mdts;
    
    public static List<MDTemple> sqls;
    @Override
    public int weight() {
        return StaticText.WEIGHT_TEMPLE;
    }

    @Override
    public Body handle(String order, Request request) throws Exception {
        String content = request.getText().getContent();
        if (mdts == null)
            init();
        for (MDTemple mdTemple : mdts) {
            if (ELUtil.check(content, mdTemple.getElNode())) {
                return getBody(mdTemple);
            }
        }
        return StaticText.EMPTY_BODY;
    }
    
    public void init() {
        Iterable<Temple> temples = templeRepository.findAll();
        mdts = new ArrayList<>();
        sqls = new ArrayList<>();
        temples.forEach(t -> {
            MDTemple mdt = new MDTemple();
            mdt.setEl(t.getEl());
            mdt.setTemple(t.getTemple());
            mdt.setMsgtype(t.getMsgtype());
            mdt.setElNode(ELUtil.getNode(t.getEl()));
            mdt.setTitle(t.getTitle());
            mdt.setPicUrl(t.getPicUrl());
            mdt.setMessageUrl(t.getMessageUrl());
            mdts.add(mdt);
            if ("sql".equals(t.getMsgtype()))
                sqls.add(mdt);
        });
    }
    
    private Body getBody(MDTemple mdTemple) {
        Body body = new Body();
        body.setMsgtype(mdTemple.getMsgtype());
        String temple = mdTemple.getTemple();
        String unescapeText = StringEscapeUtils.unescapeJava(temple);
        switch (mdTemple.getMsgtype()) {
        case "text":
            body.setText(Text.builder().content(unescapeText).build());
            break;
        case "markdown":
            body.setMarkdown(MarkDown.builder().text(unescapeText).title(mdTemple.getTitle()).build());
        case "sql":
            body.setMarkdown(MarkDown.builder().text(unescapeText).title(mdTemple.getTitle()).build());
            body.setMsgtype("markdown");
        case "link":
            body.setLink(Link.builder().text(unescapeText).title(mdTemple.getTitle()).picUrl(mdTemple.getPicUrl())
                    .messageUrl(mdTemple.getMessageUrl()).build());
        default:
            break;
        }
        return body;
    }
}
