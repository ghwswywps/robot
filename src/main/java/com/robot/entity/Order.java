package com.robot.entity;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String name;
    private List<String> args;
    private Action action;
    
    @FunctionalInterface
    public static interface Action {
        Body get(Map<String, String> property);
    }
}
