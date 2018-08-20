package com.robot.entity;

import lombok.Data;

@Data
public class Body {
    private String msgtype;
    private Text text;
    private MarkDown markdown;
    private At at;
    

}
