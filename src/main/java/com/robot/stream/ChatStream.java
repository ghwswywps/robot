package com.robot.stream;

import com.robot.entity.Body;
import com.robot.entity.Request;

/**
 * 会话流，实现此流增加处理回调消息的流程
 */
public interface ChatStream {
    int weight();
    Body handle(String order, Request request) throws Exception;
}
