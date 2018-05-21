package com.gold.cn.service;

import com.gold.cn.model.AgCNY;

import java.sql.Timestamp;
import java.util.List;

public interface AgCNYService {
    AgCNY getByTime(Timestamp timestamp);

    List<AgCNY> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<AgCNY> getRecentDayAgCNY();

    List<AgCNY> getLatestAgCNYData(Timestamp timestamp);

    int insert(AgCNY agCNY);

    int deleteByTime(Timestamp timestamp);
}
