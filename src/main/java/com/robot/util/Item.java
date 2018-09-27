package com.robot.util;

import java.util.List;

import lombok.Data;

@Data
public class Item {
    private String Name;
    private List<Item> item;
    private Request request;
    private List<Object> response;
}
