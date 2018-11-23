package com.robot.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.Link;
import com.robot.entity.MDTemple;
import com.robot.entity.MarkDown;
import com.robot.entity.Order;
import com.robot.entity.Order.Power;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.entity.TuLingBody;
import com.robot.entity.User;
import com.robot.helper.PowerHelper;
import com.robot.helper.SubscriberHelper;
import com.robot.util.ELUtil;

@Component
public class ContentHandler {
    @Autowired
    private TempleRepository templeRepository;
    @Autowired
    private SubscriberHelper subscriberHelper;
    @Autowired
    private PowerHelper powerHelper;
    
    @Value("${robot.tuling.apikey}")
    private String tulingApikey;

    public static List<MDTemple> mdts;
    
    public static List<MDTemple> sqls;

    public Body getBodyByRequest(Request request) {
        Body body = new Body();
        try {
            body = handleBody(request);
        } catch (Exception e) {
            body.setMsgtype("text");
            body.setText(Text.builder().content(e.getMessage()).build());
            e.printStackTrace();
        }
        body.setAt(At.builder().atDingtalkIds(Arrays.asList(request.getSenderId())).build());
        return body;
    }
    

    private Body handleBody(Request request) throws Exception {
        String content = request.getText().getContent();
        if (StringUtils.isEmpty(content.trim())) {
            request.getText().setContent("帮助列表");
            content = request.getText().getContent();
        }
        
        String senderId = request.getSenderId();
        Power power = powerHelper.getPowerByUserId(senderId);
        List<User> atUsers = request.getAtUsers();
        String chatbotUserId = request.getChatbotUserId();
        atUsers.removeIf(user -> user.getDingtalkId().equals(chatbotUserId));
        
        // 优先级0：加班餐 ===================================
        switch (content.trim()) {
        case "订阅加班餐":
            return subscriberHelper.subscribe(request);
        case "取消订阅加班餐":
            return subscriberHelper.unSubscribe(request);
        default:
            break;
        }
        
        // 优先级1：order ===================================
        Map<String, Order> orderMap = OrderHandler.orderMap;
        for (String key : orderMap.keySet()) {
            if (content.trim().startsWith(key)) {
                Order order = orderMap.get(key);
                Power orderPower = order.getPower();
                if(orderPower.getId() < power.getId())
                    break;
                Map<String, String> property = getProperty(content);
                property.put("atUsers", JSON.toJSONString(atUsers));
                checkArgs(order.getArgs(), property);
                return order.getAction().get(property);
            }
        }
        // 优先级2：el ===================================
        if (mdts == null)
            init();
        for (MDTemple mdTemple : mdts) {
            if (ELUtil.check(content, mdTemple.getElNode())) {
                return getBody(mdTemple);
            }
        }
        // 优先级3：tuling ===================================
        Body body = new Body();
        body.setMsgtype("text");
        body.setText(Text.builder().content(tuling(content)).build());
        return body;
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

    private void checkArgs(List<String> args, Map<String, String> property) throws Exception {
        List<String> needArgs = args.stream().filter(arg -> arg.startsWith("*")).collect(Collectors.toList());
        for (String p : needArgs) {
            if (property.get(p.split("\\*")[1]) == null)
                throw new Exception("参数非法，请检查必要参数！");
        }
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

    private Map<String, String> getProperty(String content) throws Exception {
        Pattern pattern = Pattern.compile("\"\"\"([\\s\\S]*?)\"\"\"");
        Matcher matcher = pattern.matcher(content);
        Map<String, String> valueTable = new HashMap<>();
        int t = 0;
        while (matcher.find()) {
            String v = matcher.group(1);
            String tableName = "{__tableName__" + t + "}";
            valueTable.put(tableName, v);
            content = matcher.replaceFirst(tableName);
            matcher = pattern.matcher(content);
            t++;
        }
        Map<String, String> p = new HashMap<>();
        String[] split = content.trim().split("[\\s\t\n￥]+");
        for (int i = 1; i < split.length; i++) {
            String v = split[i];
            if (!v.contains(":::"))
                throw new Exception("参数非法:" + v);
            String[] kv = v.split(":::");
            if (valueTable.get(kv[1]) == null)
                p.put(kv[0], kv[1]);
            else
                p.put(kv[0], valueTable.get(kv[1]));
        }
        return p;
    }
    
    public String tuling(String context) throws Exception {
        if (tulingApikey == null)
            return "不知道你在说啥";
        String url = "http://www.tuling123.com/openapi/api?key=" + tulingApikey + "&info="
                + URLEncoder.encode(context.trim(), "utf-8");
        StringBuffer sb = null;
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
