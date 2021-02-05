package com.zht.emqdemo.rds.service.impl;

import com.zht.emqdemo.rds.mapper.UploadRecordMapper;
import com.zht.emqdemo.rds.model.UploadRecord;
import com.zht.emqdemo.rds.service.UploadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadRecordServiceImpl implements UploadRecordService {

    @Autowired
    UploadRecordMapper uploadRecordMapper;

    @Override
    public UploadRecord findById(String id) {
        return uploadRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(UploadRecord uploadRecord) {
        return uploadRecordMapper.updateByPrimaryKeySelective(uploadRecord);
    }

    @Override
    public int deleteById(String id) {
        return uploadRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UploadRecord uploadRecord) {
        return uploadRecordMapper.insertSelective(uploadRecord);
    }
}

