package com.robot.util;

import java.util.List;

import lombok.Data;

@Data
public class Request {
    private String method;
    private List<Header> header;
    private Body body;
    private String url;
    private String description;
}
