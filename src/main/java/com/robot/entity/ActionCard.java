package com.robot.entity;

import java.util.List;

import lombok.Data;

@Data
public class ActionCard {
    private String title;
    private String text;
    private String hideAvatar;
    private String btnOrientation;
    private String singleTitle;
    private String singleURL;
    private List<Btn> btns;
}
