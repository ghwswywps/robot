package com.robot.entity.tl;

import java.util.Map;

import lombok.Data;

@Data
public class Intent {
    private String code;
    private String intentName;
    private String actionName;
    private Map<String, String> parameters;
}
