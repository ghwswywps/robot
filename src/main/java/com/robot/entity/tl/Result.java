package com.robot.entity.tl;

import java.util.Map;

import lombok.Data;

@Data
public class Result {
    private String resultType;
    private Map<String, String> values;
    private int groupType;
}
