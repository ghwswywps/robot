package com.robot.entity.confluence;

import lombok.Data;

@Data
public class ConfluenceRequest {
    private String type;
    private String title;
    private String id;
    private Space space;
    private Body body;
    private Version version;
}
