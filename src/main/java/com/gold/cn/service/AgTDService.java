package com.gold.cn.service;

import com.gold.cn.model.AgTD;

import java.sql.Timestamp;
import java.util.List;

public interface AgTDService {
    AgTD getByTime(Timestamp timestamp);

    List<AgTD> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<AgTD> getRecentDayAgTD();

    List<AgTD> getLatestAgTDData(Timestamp timestamp);

    List<AgTD> getNewestAgTDData(long id);

    int insert(AgTD agTD);

    int deleteByTime(Timestamp timestamp);
}
