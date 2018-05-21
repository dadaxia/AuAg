package com.gold.cn.service;

import com.gold.cn.model.AuTD;

import java.sql.Timestamp;
import java.util.List;

public interface AuTDService {
    AuTD getByTime(Timestamp timestamp);

    List<AuTD> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<AuTD> getRecentDayAuTD();

    List<AuTD> getLatestAuTDData(Timestamp timestamp);

    int insert(AuTD auTD);

    int deleteByTime(Timestamp timestamp);
}
