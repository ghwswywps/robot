package com.robot.entity.tl;

import java.util.List;

import lombok.Data;

@Data
public class Response {
    private Intent intent;
    private List<Result> results; 
}
