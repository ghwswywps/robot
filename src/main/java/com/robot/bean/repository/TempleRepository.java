package com.robot.bean.repository;

import org.springframework.data.repository.CrudRepository;

import com.robot.bean.Temple;

/**
 * 模板表DAO层
 */
public interface TempleRepository extends CrudRepository<Temple,Long>{
}
