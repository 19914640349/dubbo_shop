package com.qf.service;

import com.qf.entity.Power;

import java.util.List;

public interface IPowerService {

    List<Power> queryAllPower();

    void addPower(Power power);

    List<Power> queryPowersByRid(Integer rid);

    void deletePower(Integer id);
}
