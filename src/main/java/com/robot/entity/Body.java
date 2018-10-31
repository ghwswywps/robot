package com.robot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Body {
    private String msgtype;
    private Text text;
    private MarkDown markdown;
    private At at;
    private Link link;
    

}
