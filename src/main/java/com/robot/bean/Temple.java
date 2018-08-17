package com.robot.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Temple {
    @Id
    @GeneratedValue
    private long id;
    
    private String el;
    
    private String msgtype;

    private String temple;
}
