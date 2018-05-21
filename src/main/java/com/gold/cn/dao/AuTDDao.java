package com.gold.cn.dao;

import com.gold.cn.model.AuTD;

import java.sql.Timestamp;
import java.util.List;

public interface AuTDDao {
    AuTD getByTime(Timestamp timestamp);

    List<AuTD> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<AuTD> getRecentDayAuTD();

    List<AuTD> getLatestAuTDData(Timestamp timestamp);

    int insert(AuTD auTD);

    int deleteByTime(Timestamp timestamp);
}
