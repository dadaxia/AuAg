package com.gold.cn.service.impl;

import com.gold.cn.dao.AgDao;
import com.gold.cn.model.Ag;
import com.gold.cn.service.AgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AgServiceImpl implements AgService {
    @Autowired
    private AgDao agDao;

    @Override
    public Ag getByTime(Timestamp timestamp) {
        return agDao.getByTime(timestamp);
    }

    @Override
    public List<Ag> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return agDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<Ag> getRecentDayAg() {
        return agDao.getRecentDayAg();
    }

    @Override
    public List<Ag> getLatestAgData(Timestamp timestamp) {
        return agDao.getLatestAgData(timestamp);
    }

    @Override
    public int insert(Ag ag) {
        return agDao.insert(ag);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return agDao.deleteByTime(timestamp);
    }
}
