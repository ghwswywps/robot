package com.robot.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robot.bean.PowerEntity;
import com.robot.bean.repository.PowerEntityRepository;
import com.robot.entity.Order.Power;
import com.robot.entity.User;

@Component
public class PowerHelper {
    @Autowired
    private PowerEntityRepository powerEntityRepository;

    public Power getPowerByUserId(String userId) {
        PowerEntity findByUserId = powerEntityRepository.findByUserId(userId);
        if (findByUserId == null)
            return Power.USER;
        return Power.getById(findByUserId.getId());
    }

    public void save(Power power, List<User> atUsers) {
        powerEntityRepository.save(atUsers.stream().map(user -> new PowerEntity(0, user.getDingtalkId(), power.getId()))
                .collect(Collectors.toList()));
    }
}
