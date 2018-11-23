package com.robot.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionCard {
    private String title;
    private String text;
    private String hideAvatar;
    private String btnOrientation;
    private String singleTitle;
    private String singleURL;
    private List<Btn> btns;
}
