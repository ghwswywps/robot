package com.robot.bean.repository;

import org.springframework.data.repository.CrudRepository;

import com.robot.bean.PowerEntity;

public interface PowerEntityRepository extends CrudRepository<PowerEntity,Long>{
    public PowerEntity findByUserId(String userId);
}
