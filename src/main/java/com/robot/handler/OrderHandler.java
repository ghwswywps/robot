package com.robot.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.Body;
import com.robot.entity.MarkDown;
import com.robot.entity.Order;
import com.robot.entity.Order.Power;
import com.robot.entity.Text;
import com.robot.entity.User;
import com.robot.helper.PowerHelper;
import com.robot.util.ColorUtil;
import com.robot.util.DingUtil;
import com.robot.util.PostManGenUtil;
import com.robot.util.SqlFormatUtil;

@Component
public class OrderHandler implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static Map<String, Order> orderMap = new HashMap<>();
    static {
        orderMap.put("增加TEXT模板", Order
                .builder()
                .args(Arrays.asList("\\*temple", "\\*el"))
                .name("增加TEXT模板")
                .action(p -> {
                    Body body = new Body();
                    getTempleRepository().save(Temple
                            .builder()
                            .el(p.get("el"))
                            .msgtype("text")
                            .temple(p.get("temple"))
                            .build());
                    getContentHandler().init();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("添加成功").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("增加MARKDOWN模板", Order
                .builder()
                .args(Arrays.asList("\\*temple", "\\*el", "\\*title"))
                .name("增加MARKDOWN模板")
                .action(p -> {
                    Body body = new Body();
                    getTempleRepository().save(Temple
                            .builder()
                            .el(p.get("el"))
                            .msgtype("markdown")
                            .temple(p.get("temple"))
                            .title(p.get("title"))
                            .build());
                    getContentHandler().init();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("添加成功").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("增加LINK模板", Order
                .builder()
                .args(Arrays.asList("\\*temple", "\\*el", "\\*title", "\\*messageUrl", "picUrl"))
                .name("增加LINK模板")
                .action(p -> {
                    Body body = new Body();
                    getTempleRepository().save(Temple
                            .builder()
                            .el(p.get("el"))
                            .msgtype("link")
                            .temple(p.get("temple"))
                            .title(p.get("title"))
                            .messageUrl(p.get("messageUrl"))
                            .picUrl(p.get("picUrl"))
                            .build());
                    getContentHandler().init();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("添加成功").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("增加SQL", Order
                .builder()
                .args(Arrays.asList("\\*sql","\\*title"))
                .name("增加SQL")
                .action(p -> {
                    Body body = new Body();
                    getTempleRepository().save(Temple
                            .builder()
                            .el("*" + p.get("title"))
                            .msgtype("sql")
                            .temple("## " + p.get("title") + "\n\n-----\n" + 
                                    SqlFormatUtil.format(p.get("sql")))
                            .title(p.get("title"))
                            .build());
                    getContentHandler().init();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("添加成功").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("常用SQL列表", Order
                .builder()
                .args(Arrays.asList())
                .name("常用SQL列表")
                .action(p -> {
                    Body body = new Body();
                    StringBuilder res = new StringBuilder();
                    res.append("## 常用SQL列表\n\n-----\n");
                    if (ContentHandler.sqls == null)
                        getContentHandler().init();
                    
                    ContentHandler.sqls.forEach(t -> {
                        res.append("> - " + DingUtil.getSendingLinkInMD(t.getTitle()) + "  \n");
                    });
                    body.setMsgtype("markdown");
                    body.setMarkdown(MarkDown.builder().text(res.toString()).title("常用SQL列表").build());
                    return body;
                })
                .power(Power.USER)
                .build());
        orderMap.put("模板列表", Order
                .builder()
                .args(Arrays.asList("id", "ids"))
                .name("模板列表")
                .action(p -> {
                    Body body = new Body();
                    StringBuilder res = new StringBuilder();
                    res.append("## 模板列表\n\n-----\n\n");
                    if (p.get("id") != null) {
                        Temple t = getTempleRepository().findOne(Long.parseLong(p.get("id")));
                        if (t == null) {
                            body.setMsgtype("text");
                            body.setText(Text.builder().content("未找到模板").build());
                            return body;
                        }
                        handleTempleShow(res, t);
                    } else if (p.get("ids") != null) {
                        Iterable<Temple> ts = getTempleRepository().findAll(JSON.parseObject(p.get("ids"), new TypeReference<List<Long>>() {}));
                        ts.forEach(t -> {
                            handleTempleShow(res, t);
                        });
                    } else {
                        getTempleRepository().findAll().forEach(t -> {
                            res.append("> " + ColorUtil.getOrange("id") + ":" + ColorUtil.getBlue(t.getId() + "") + "  \n");
                            res.append("> " + ColorUtil.getOrange("el") + ":"
                                    + DingUtil.getSendingLinkInMD(t.getEl(), "模板列表 id:::" + t.getId()) + "  \n");
                            res.append("> \n"
                                    +  "> -----  "
                                    +  "\n");
                        });
                    }
                    body.setMsgtype("markdown");
                    body.setMarkdown(MarkDown.builder().text(res.toString().replaceAll("\\*", "\\\\\\*")).title("模板列表").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("删除模板", Order
                .builder()
                .args(Arrays.asList("\\*id"))
                .name("删除模板")
                .action(p -> {
                    Body body = new Body();
                    getTempleRepository().delete(Long.parseLong(p.get("id")));
                    getContentHandler().init();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("操作成功").build());
                    return body;
                })
                .power(Power.ADMIN)
                .build());
        orderMap.put("机器人指令", Order
                .builder()
                .args(Arrays.asList())
                .name("机器人指令")
                .action(p -> {
                    Body body = new Body();
                    body.setMsgtype("markdown");
                    StringBuilder res = new StringBuilder();
                    res.append("### 机器人指令\n" + 
                            "------\n");
                    Arrays.asList("指令帮助", "机器人指令", "模板帮助", "模板列表", "删除模板", "增加TEXT模板", "增加MARKDOWN模板", "增加LINK模板"
                            , "增加SQL", "常用SQL列表", "授权", "PostManGen")
                            .forEach(k -> {
                                Order v = orderMap.get(k);
                                res.append((res.length() > 0 ? "\n" : "") + "- 指令:" + v.getName() + ",参数"
                                        + v.getArgs().toString());
                            });
                    body.setMarkdown(MarkDown.builder().text(res.toString()).title("机器人指令").build());
                    return body;
                })
                .power(Power.USER)
                .build());
        orderMap.put("帮助列表", Order
                .builder()
                .args(Arrays.asList())
                .name("帮助列表")
                .action(p -> {
                    Body body = new Body();
                    body.setMsgtype("markdown");
                    StringBuilder res = new StringBuilder();
                    res.append("### 帮助列表(键入或点击以下指令获取帮助)\n" + 
                            "------\n" + 
                            "1. [机器人指令](dtmd://dingtalkclient/sendMessage?content=机器人指令)\n" + 
                            "2. [指令帮助](dtmd://dingtalkclient/sendMessage?content=指令帮助)\n" + 
                            "3. [模板帮助](dtmd://dingtalkclient/sendMessage?content=模板帮助)");
                    body.setMarkdown(MarkDown.builder().text(res.toString()).title("帮助列表").build());
                    return body;
                })
                .power(Power.USER)
                .build());
        orderMap.put("指令帮助", Order
                .builder()
                .args(Arrays.asList())
                .name("指令帮助")
                .action(p -> {
                    Body body = new Body();
                    body.setMsgtype("markdown");
                    StringBuilder res = new StringBuilder();
                    res.append("### 指令帮助\n" + 
                            "------\n" + 
                            "1. 指令格式: @机器人 order k1:::v1 k2:::v2 ...\n" + 
                            "2. 带*参数为必须参数\n" +
                            "3. 若参数有特殊字符，可以使用JAVA转义替换，如空格可用\\40替换\n" + 
                            "4. 可以使用\"\"\"${value}\"\"\"的格式 来替代转义的方式");
                    body.setMarkdown(MarkDown.builder().text(res.toString()).title("指令帮助").build());
                    return body;
                })
                .power(Power.USER)
                .build());
        orderMap.put("模板帮助", Order
                .builder()
                .args(Arrays.asList())
                .name("模板帮助")
                .action(p -> {
                    Body body = new Body();
                    body.setMsgtype("markdown");
                    StringBuilder res = new StringBuilder();
                    res.append("### 模板帮助\n" + 
                            "------\n" + 
                            "1. `el`支持`&|()`组合 ,带*为全行匹配,否则为包含匹配\n" + 
                            "```example\n" + 
                            "  el=*帮助大全|(帮助&列表)\n" + 
                            "    在键入\"帮助大全\"或者\"有没有列表可以帮助我\"可以匹配\n" + 
                            "```\n" + 
                            "2. 一般来说，`temple`参数为通用实体，作为所有模板的主显示体为用");
                    body.setMarkdown(MarkDown.builder().text(res.toString()).title("模板帮助").build());
                    return body;
                })
                .power(Power.USER)
                .build());
        orderMap.put("授权", Order
                .builder()
                .args(Arrays.asList("*power"))
                .name("授权")
                .action(p -> {
                    String powerStr = p.get("power");
                    Power power = Power.getByName(powerStr);
                    List<User> atUsers = JSON.parseObject(p.get("atUsers"), new TypeReference<List<User>>() {});
                    getPowerHelper().save(power, atUsers);
                    Body body = new Body();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content("添加成功").build());
                    return body;
                })
                .power(Power.MASTER)
                .build());
        orderMap.put("PostManGen", Order
                .builder()
                .args(Arrays.asList("*mavenXml","port","relayOn"))
                .name("PostManGen")
                .action(p -> {
                    String mavenXml = p.get("mavenXml");
                    String port = p.get("port");
                    if (port == null)
                        port = "80";
                    String relayOn = p.get("relayOn");
                    String postManConfigByMavenXml = null;
                    try {
                        postManConfigByMavenXml = new PostManGenUtil().getPostManConfigByMavenXml(mavenXml, Integer.parseInt(port), relayOn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        postManConfigByMavenXml = e.getMessage();
                    }
                    Body body = new Body();
                    body.setMsgtype("text");
                    body.setText(Text.builder().content(postManConfigByMavenXml).build());
                    return body;
                })
                .power(Power.USER)
                .build());
        
        
    }

    private static void handleTempleShow(StringBuilder res, Temple t) {
        res.append("> " + ColorUtil.getOrange("id") + ":" + t.getId() + "  \n");
        res.append("> " + ColorUtil.getOrange("el") + ":" + t.getEl() + "  \n");
        res.append("> msgtype:" + t.getMsgtype() + "  \n");
        res.append("> pic_url:" + t.getPicUrl() + "  \n");
        res.append("> messgae_url:" + t.getMessageUrl() + "  \n");
        res.append("> temple:  \n> ```  \n"
                + "> " + t.getTemple().replaceAll("```", "\\`\\`\\`").replaceAll("(?!\\\\)\\n", "\n > ")
                + "  \n\n");
        res.append("> ```  \n");
        res.append("> \n\n-----\n");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (OrderHandler.applicationContext == null) {
            OrderHandler.applicationContext = applicationContext;
        }
    }

    public static TempleRepository getTempleRepository() {
        return applicationContext.getBean(TempleRepository.class);
    }
    
    public static ContentHandler getContentHandler() {
        return applicationContext.getBean(ContentHandler.class);
    }
    
    public static PowerHelper getPowerHelper() {
        return applicationContext.getBean(PowerHelper.class);
    }
}
