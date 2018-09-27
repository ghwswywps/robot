package com.robot.util;

import java.util.Map;

import lombok.Data;

@Data
public class Raw {
    private String ver;
    private String iface;
    private String method;
    private Map<String, String> args;
}
