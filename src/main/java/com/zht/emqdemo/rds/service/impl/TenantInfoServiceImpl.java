package com.zht.emqdemo.rds.service.impl;

import com.zht.emqdemo.rds.mapper.TenantInfoMapper;
import com.zht.emqdemo.rds.model.TenantInfo;
import com.zht.emqdemo.rds.service.TenantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantInfoServiceImpl implements TenantInfoService {

    @Autowired
    TenantInfoMapper tenantInfoMapper;

    @Override
    public TenantInfo findById(String id) {
        return tenantInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TenantInfo tenantInfo) {
        return tenantInfoMapper.updateByPrimaryKeySelective(tenantInfo);
    }

    @Override
    public int deleteById(String id) {
        return tenantInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TenantInfo tenantInfo) {
        return tenantInfoMapper.insertSelective(tenantInfo);
    }
}
