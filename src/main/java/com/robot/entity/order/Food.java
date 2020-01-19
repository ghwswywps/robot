package com.robot.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Food
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private String name;
    private double price;
    private int foodNumber;
    private boolean isGet;
}