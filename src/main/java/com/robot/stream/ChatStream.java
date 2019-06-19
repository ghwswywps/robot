package com.robot.stream;

import com.robot.entity.Body;
import com.robot.entity.Request;

public interface ChatStream {
    int weight();
    Body handle(String order, Request request) throws Exception;
}
