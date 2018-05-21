package com.gold.cn.service.impl;

import com.gold.cn.dao.AuTDDao;
import com.gold.cn.model.AuTD;
import com.gold.cn.service.AuTDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AuTDServiceImpl implements AuTDService {
    @Autowired
    private AuTDDao auTDDao;
    @Override
    public AuTD getByTime(Timestamp timestamp) {
        return auTDDao.getByTime(timestamp);
    }

    @Override
    public List<AuTD> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return auTDDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<AuTD> getRecentDayAuTD() {
        return auTDDao.getRecentDayAuTD();
    }

    @Override
    public List<AuTD> getLatestAuTDData(Timestamp timestamp) {
        return auTDDao.getLatestAuTDData(timestamp);
    }

    @Override
    public int insert(AuTD auTD) {
        return auTDDao.insert(auTD);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return auTDDao.deleteByTime(timestamp);
    }
}
