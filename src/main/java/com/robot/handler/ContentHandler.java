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
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.At;
import com.robot.entity.Body;
import com.robot.entity.MDTemple;
import com.robot.entity.MarkDown;
import com.robot.entity.Order;
import com.robot.entity.Request;
import com.robot.entity.Text;
import com.robot.entity.TuLingBody;
import com.robot.util.ELUtil;

@Component
public class ContentHandler {
    @Autowired
    private TempleRepository templeRepository;

    public static List<MDTemple> mdts;

    public Body getBodyByRequest(Request request) {
        Body body = new Body();
        try {
            String content = request.getText().getContent();
            body = handleBody(content);
        } catch (Exception e) {
            body.setMsgtype("text");
            body.setText(Text.builder().content(e.getMessage()).build());
        }
        body.setAt(At.builder().atDingtalkIds(Arrays.asList(request.getSenderId())).build());
        return body;
    }

    private Body handleBody(String content) throws Exception {
        
        // 优先级1：order ===================================
        Map<String, Order> orderMap = OrderHandler.orderMap;
        for (String key : orderMap.keySet()) {
            if (content.trim().startsWith(key)) {
                Order order = orderMap.get(key);
                Map<String, String> property = getProperty(content);
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
            body.setMarkDown(MarkDown.builder().text(unescapeText).title(mdTemple.getTitle()).build());
        default:
            break;
        }
        return body;
    }

    private void checkArgs(List<String> args, Map<String, String> property) throws Exception {
        List<String> needArgs = args.stream().filter(arg -> arg.startsWith("\\*")).collect(Collectors.toList());
        for (String p : needArgs) {
            if (property.get(p) == null)
                throw new Exception("参数非法，请检查必要参数！");
        }
    }

    public void init() {
        Iterable<Temple> temples = templeRepository.findAll();
        mdts = new ArrayList<>();
        temples.forEach(t -> {
            MDTemple mdt = new MDTemple();
            mdt.setEl(t.getEl());
            mdt.setTemple(t.getTemple());
            mdt.setMsgtype(t.getMsgtype());
            mdt.setElNode(ELUtil.getNode(t.getEl()));
            mdt.setTitle(t.getTitle());
            mdts.add(mdt);
        });
    }

    private Map<String, String> getProperty(String content) throws Exception {
        Map<String, String> p = new HashMap<>();
        String[] split = content.trim().split("[\\s\t\n]+");
        for (int i = 1; i < split.length; i++) {
            String v = split[i];
            if (!v.contains(":::"))
                throw new Exception("参数非法:" + v);
            String[] kv = v.split(":::");
            p.put(kv[0], kv[1]);
        }
        return p;
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
