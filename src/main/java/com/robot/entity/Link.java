package com.robot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private String title;
    private String text;
    private String messageUrl;
    private String picUrl;
}
