package com.gold.cn.service;

import com.gold.cn.model.Au;

import java.sql.Timestamp;
import java.util.List;

/**
 * Au Service
 */
public interface AuService {
    Au httpGetAuData();

    Au getByTime(Timestamp timestamp);

    List<Au> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<Au> getRecentDayAu();

    List<Au> getLatestAuData(Timestamp timestamp);

    List<Au> getNewestAuData(long id);

    int insert(Au au);

    int deleteByTime(Timestamp timestamp);
}
