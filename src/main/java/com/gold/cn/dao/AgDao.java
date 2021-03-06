package com.gold.cn.dao;


import com.gold.cn.model.Ag;

import java.sql.Timestamp;
import java.util.List;

public interface AgDao {
    Ag getByTime(Timestamp timestamp);

    List<Ag> getByTimeInterval(Timestamp startTime, Timestamp endTime);

    List<Ag> getRecentDayAg();

    List<Ag> getLatestAgData(Timestamp timestamp);

    int insert(Ag ag);

    int deleteByTime(Timestamp timestamp);
}
