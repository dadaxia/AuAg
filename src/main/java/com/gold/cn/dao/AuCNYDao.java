package com.gold.cn.dao;

import com.gold.cn.model.AuCNY;

import java.sql.Timestamp;
import java.util.List;

public interface AuCNYDao {
    AuCNY getByTime(Timestamp timestamp);

    List<AuCNY> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<AuCNY> getRecentDayAuCNY();

    List<AuCNY> getLatestAuCNYData(Timestamp timestamp);

    int insert(AuCNY auCNY);

    int deleteByTime(Timestamp timestamp);
}
