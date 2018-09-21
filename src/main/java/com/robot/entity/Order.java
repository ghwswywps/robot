package com.robot.entity;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String name;
    private List<String> args;
    private Action action;
    private Power power;
    
    @FunctionalInterface
    public static interface Action {
        Body get(Map<String, String> property);
    }
    
    @AllArgsConstructor
    @Getter
    public static enum Power {
        MASTER(0,"MASTER"),
        ADMIN(1,"ADMIN"),
        USER(2,"USER");
        ;
        private int id;
        private String name;
        
        public static Power getById(long id) {
            Power[] values = Power.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getId() == id)
                    return values[i];
            }
            return USER;
        }

        public static Power getByName(String name) {
            
            Power[] values = Power.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].getName().toUpperCase() == name.trim().toUpperCase())
                    return values[i];
            }
            return USER;
        }
    }
}
