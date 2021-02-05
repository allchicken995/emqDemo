package com.zht.emqdemo.rds.service;

import com.zht.emqdemo.rds.model.TenantInfo;

public interface TenantInfoService {

    TenantInfo findById(String id);

    int update(TenantInfo tenantInfo);

    int deleteById(String id);

    int insert(TenantInfo tenantInfo);

}
