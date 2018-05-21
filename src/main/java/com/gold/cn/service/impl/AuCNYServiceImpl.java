package com.gold.cn.service.impl;

import com.gold.cn.dao.AuCNYDao;
import com.gold.cn.model.AuCNY;
import com.gold.cn.service.AuCNYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AuCNYServiceImpl implements AuCNYService {
    @Autowired
    private AuCNYDao auCNYDao;
    @Override
    public AuCNY getByTime(Timestamp timestamp) {
        return auCNYDao.getByTime(timestamp);
    }

    @Override
    public List<AuCNY> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return auCNYDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<AuCNY> getRecentDayAuCNY() {
        return auCNYDao.getRecentDayAuCNY();
    }

    @Override
    public List<AuCNY> getLatestAuCNYData(Timestamp timestamp) {
        return auCNYDao.getLatestAuCNYData(timestamp);
    }

    @Override
    public int insert(AuCNY auCNY) {
        return auCNYDao.insert(auCNY);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return auCNYDao.deleteByTime(timestamp);
    }
}
