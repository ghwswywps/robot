package com.robot.entity.tl;

import lombok.Data;

@Data
public class TLBody {
    private Integer reqType;
    private Perception perception;
    private UserInfo userInfo;
    
}
