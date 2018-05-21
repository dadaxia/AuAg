package com.gold.cn.service;

import com.gold.cn.model.USD;

import java.sql.Timestamp;
import java.util.List;

public interface USDService {
    USD getByTime(Timestamp timestamp);

    List<USD> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<USD> getRecentDayUSD();

    List<USD> getLatestUSDData(Timestamp timestamp);

    int insert(USD usd);

    int deleteByTime(Timestamp timestamp);
}
