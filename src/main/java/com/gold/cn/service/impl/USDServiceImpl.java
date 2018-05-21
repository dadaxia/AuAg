package com.gold.cn.service.impl;

import com.gold.cn.dao.USDDao;
import com.gold.cn.model.USD;
import com.gold.cn.service.USDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class USDServiceImpl implements USDService {
    @Autowired
    private USDDao usdDao;
    @Override
    public USD getByTime(Timestamp timestamp) {
        return usdDao.getByTime(timestamp);
    }

    @Override
    public List<USD> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return usdDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<USD> getRecentDayUSD() {
        return usdDao.getRecentDayUSD();
    }

    @Override
    public List<USD> getLatestUSDData(Timestamp timestamp) {
        return usdDao.getLatestUSDData(timestamp);
    }

    @Override
    public int insert(USD usd) {
        return usdDao.insert(usd);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return usdDao.deleteByTime(timestamp);
    }
}
