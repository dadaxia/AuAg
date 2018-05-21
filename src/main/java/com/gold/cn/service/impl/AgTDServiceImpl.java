package com.gold.cn.service.impl;

import com.gold.cn.dao.AgTDDao;
import com.gold.cn.model.AgTD;
import com.gold.cn.service.AgTDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AgTDServiceImpl implements AgTDService {

    @Autowired
    private AgTDDao agTDDao;

    @Override
    public AgTD getByTime(Timestamp timestamp) {
        return agTDDao.getByTime(timestamp);
    }

    @Override
    public List<AgTD> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return agTDDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<AgTD> getRecentDayAgTD() {
        return agTDDao.getRecentDayAgTD();
    }

    @Override
    public List<AgTD> getLatestAgTDData(Timestamp timestamp) {
        return agTDDao.getLatestAgTDData(timestamp);
    }

    @Override
    public List<AgTD> getNewestAgTDData(long id) {
        return agTDDao.getNewestAgTDData(id);
    }

    @Override
    public int insert(AgTD agTD) {
        return agTDDao.insert(agTD);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return agTDDao.deleteByTime(timestamp);
    }
}
