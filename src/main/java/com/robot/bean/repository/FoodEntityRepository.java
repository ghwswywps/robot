package com.robot.bean.repository;

import com.robot.bean.FoodEntity;

import org.springframework.data.repository.CrudRepository;

/** 
 * 菜单表DAO层
 */
public interface FoodEntityRepository extends CrudRepository<FoodEntity, Long> {
}
