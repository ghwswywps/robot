package com.robot.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Temple {
    @Id
    @GeneratedValue
    private long id;
    
    private String el;
    
    private String msgtype;
    
    private String temple;
    
    private String title;
}
