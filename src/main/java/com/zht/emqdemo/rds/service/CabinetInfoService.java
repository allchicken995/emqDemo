package com.zht.emqdemo.rds.service;


import com.zht.emqdemo.rds.model.CabinetInfo;

public interface CabinetInfoService {

    CabinetInfo findById(String id);

    int update(CabinetInfo cabinetInfo);

    int deleteById(String id);

    int insert(CabinetInfo cabinetInfo);

}
