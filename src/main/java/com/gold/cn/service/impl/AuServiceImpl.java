package com.gold.cn.service.impl;

import com.gold.cn.dao.AuDao;
import com.gold.cn.model.Au;
import com.gold.cn.service.AuService;
import com.gold.cn.util.JsoupConnectUrl;
import com.gold.cn.util.PropertyConfigure;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Au ServiceImpl
 */
@Service
public class AuServiceImpl implements AuService {

    @Autowired
    private AuDao auDao;

    @Override
    public Au getByTime(Timestamp timestamp) {
        return auDao.getByTime(timestamp);
    }

    @Override
    public List<Au> getByTimeInterval(Timestamp startTime, Timestamp endTime) {
        return auDao.getByTimeInterval(startTime,endTime);
    }

    @Override
    public List<Au> getRecentDayAu() {
        return auDao.getRecentDayAu();
    }

    @Override
    public int insert(Au au) {
        return auDao.insert(au);
    }

    @Override
    public int deleteByTime(Timestamp timestamp) {
        return auDao.deleteByTime(timestamp);
    }

    @Override
    public List<Au> getLatestAuData(Timestamp timestamp) {
        return auDao.getLatestAuData(timestamp);
    }

    @Override
    public List<Au> getNewestAuData(long id) {
        return auDao.getNewestAuData(id);
    }

    @Autowired
    private PropertyConfigure propertyConfigure;
    /**
     * 通过url获取Au数据
     * @return
     */
    @Override
    public Au httpGetAuData() {
        Au au = new Au();
        try {
            Document doc = JsoupConnectUrl.httpGet(propertyConfigure.getProperty("GET_AU_AG_DOLLAR_AND_TD"));
            if(doc == null){
                return null;
            }
            if(doc.select("li[code=XAU]").hasText()){
                au.setNowPrice(doc.select("li[code=XAU]").select(".hq_price.cor.data_1.last").text());
                au.setCode("XAU");
                au.setChangePoint(doc.select("li[code=XAU]").select(".hq_change.cor.swing").text());
                au.setChangeRate(doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text());
                au.setOpenPrice(doc.select("li[code=XAU]").select(".hq_open.black").text());
                au.setHighPrice(doc.select("li[code=XAU]").select(".hq_height.black").text());
                au.setLowPrice(doc.select("li[code=XAU]").select(".hq_low.black").text());
                au.setYesterdayClosePrice(doc.select("li[code=XAU]").select(".hq_close.black.lastt").text());
                au.setTime(new Timestamp(System.currentTimeMillis()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return au;
    }
}
