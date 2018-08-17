package com.robot.entity;

import java.util.List;

import lombok.Data;

@Data
public  class ELNode {
    private List<ELNode> nodes;
    private ELEnum elEnum;
    private String value;
    public static final ELNode OR = new ELNode();
    public static final ELNode AND = new ELNode();
    static {
        OR.setElEnum(ELEnum.or);
        AND.setElEnum(ELEnum.and);
    }
}
