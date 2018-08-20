package com.robot.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.robot.bean.Temple;
import com.robot.bean.repository.TempleRepository;
import com.robot.entity.Body;
import com.robot.entity.Order;
import com.robot.entity.Text;

@Component
public class OrderHandler implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static Map<String, Order> orderMap = new HashMap<>();
    static {
        orderMap.put("增加TEXT模板", Order
                .builder()
                .args(Arrays.asList("*temple", "*el"))
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
                .build());
        orderMap.put("增加MARKDOWN模板", Order
                .builder()
                .args(Arrays.asList("*temple", "*el", "*title"))
                .name("增加TEXT模板")
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
                .build());
        orderMap.put("指令", Order
                .builder()
                .args(Arrays.asList())
                .name("指令")
                .action(p -> {
                    Body body = new Body();
                    body.setMsgtype("text");
                    StringBuilder res = new StringBuilder();
                    orderMap.forEach((k,v) -> {
                        res.append(res.length() > 0 ? "\n" : "" + "指令:" + v.getName() + ",参数" + v.getArgs().toString());
                    });
                    body.setText(Text.builder().content(res.toString()).build());
                    return body;
                })
                .build());
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

}
