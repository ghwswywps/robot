package com.robot.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.robot.entity.tl.Response;
import com.robot.entity.tl.TLBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 封装了图灵api
 */
@Slf4j
@Component
public class TulingSender {
    
    public static String WEBHOOK_TOKEN_PRE = "http://openapi.tuling123.com/openapi/api/v2";

    public String send(TLBody body) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN_PRE);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(JSON.toJSONString(body), "utf-8");
        log.info("SenderBody:" + JSON.toJSONString(body));
        httppost.setEntity(se);
        HttpResponse execute = httpclient.execute(httppost);
        HttpEntity entity = execute.getEntity();
        String result = EntityUtils.toString(entity,"UTF-8");
        log.info(result);
        Response r = JSON.parseObject(result, Response.class);
        String text = r.getResults().get(0).getValues().get("text");
        return text;
    }
}
