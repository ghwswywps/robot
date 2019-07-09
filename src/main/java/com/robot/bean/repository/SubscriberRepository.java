package com.robot.bean.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.robot.bean.Subscriber;

/**
 * 订阅表DAO层
 */
public interface SubscriberRepository extends CrudRepository<Subscriber,Long>{
    public List<Subscriber> findByUserId(String userId);
}
