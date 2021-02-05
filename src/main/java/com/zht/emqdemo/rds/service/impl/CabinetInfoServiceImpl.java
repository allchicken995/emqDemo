package com.zht.emqdemo.rds.service.impl;

import com.zht.emqdemo.rds.mapper.CabinetInfoMapper;
import com.zht.emqdemo.rds.model.CabinetInfo;
import com.zht.emqdemo.rds.service.CabinetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CabinetInfoServiceImpl implements CabinetInfoService {

    @Autowired
    CabinetInfoMapper cabinetInfoMapper;

    @Override
    public CabinetInfo findById(String id) {
        return cabinetInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(CabinetInfo cabinetInfo) {
        return cabinetInfoMapper.updateByPrimaryKeySelective(cabinetInfo);
    }

    @Override
    public int deleteById(String id) {
        return cabinetInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CabinetInfo cabinetInfo) {
        return cabinetInfoMapper.insertSelective(cabinetInfo);
    }
}
