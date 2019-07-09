package com.robot.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人员权限Entity
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PowerEntity {
    @Id
    @GeneratedValue
    private long id;

    private String userId;

    private int powerId;

}
