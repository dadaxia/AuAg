package com.gold.cn.service.impl;

import com.gold.cn.dao.AgCNYDao;
import com.gold.cn.model.AgCNY;
import com.gold.cn.service.AgCNYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AgCNYServiceImpl implements AgCNYService {

    @Autowired
    private AgCNYDao agCNYDao;

    @Override
    public AgCNY getByTime(Timestamp timestamp) {
        return agCNYDao.getByTime(timestamp);
    }

    @Override
    public List<AgCNY> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return agCNYDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<AgCNY> getRecentDayAgCNY() {
        return agCNYDao.getRecentDayAgCNY();
    }

    @Override
    public List<AgCNY> getLatestAgCNYData(Timestamp timestamp) {
        return agCNYDao.getLatestAgCNYData(timestamp);
    }

    @Override
    public int insert(AgCNY agCNY) {
        return agCNYDao.insert(agCNY);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return agCNYDao.deleteByTime(timestamp);
    }
}
