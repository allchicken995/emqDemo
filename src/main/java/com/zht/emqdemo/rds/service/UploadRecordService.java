package com.zht.emqdemo.rds.service;

import com.zht.emqdemo.rds.model.UploadRecord;

public interface UploadRecordService {

    UploadRecord findById(String id);

    int update(UploadRecord uploadRecord);

    int deleteById(String id);

    int insert(UploadRecord uploadRecord);

}
