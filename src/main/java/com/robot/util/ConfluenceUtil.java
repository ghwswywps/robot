package com.robot.util;

import com.alibaba.fastjson.JSON;
import com.robot.entity.confluence.ConfluenceRequest;

import lombok.extern.log4j.Log4j;

@Log4j
public class ConfluenceUtil {
    public static void put(String url, String authorization, ConfluenceRequest confluenceRequest) {
        String result = NetUtil.doPut(url, authorization, JSON.toJSONString(confluenceRequest));
        log.info(result);
    }
}
