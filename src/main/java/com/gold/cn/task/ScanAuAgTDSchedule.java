package com.gold.cn.task;

import com.gold.cn.dao.*;
import com.gold.cn.model.*;
import com.gold.cn.util.JsoupConnectUrl;
import com.gold.cn.util.PropertyConfigure;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 上海黄金T+D 白银T+D
 */
@Component
public class ScanAuAgTDSchedule {

//    Logger log = Logger.getLogger(this.getClass().getName());
//
//    @Autowired
//    private PropertyConfigure propertyConfigure;
//
//    @Autowired
//    private AgTDDao agTDDao;

    @Scheduled(cron = "0/1 * * * * MON-FRI ")
    public void getAuAgData() {

//        Calendar now = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
//            long ts0000 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "00:00:00").getTime();
//            long ts0230 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "02:30:00").getTime();
//            long ts0900 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "09:00:00").getTime();
//            long ts1130 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "11:30:00").getTime();
//            long ts1330 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "13:30:00").getTime();
//            long ts1530 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "15:30:00").getTime();
//            long ts2000 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "20:00:00").getTime();
//            long ts2359 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "23:59:00").getTime();
//
//            //运行时间为周一至周五：9:00-11:30 13:30-15:30 20:00-02:00
//            long nowTimestamp = System.currentTimeMillis();
//            if ((nowTimestamp > ts0230 && nowTimestamp < ts0900) || (nowTimestamp > ts1130 && nowTimestamp < ts1330) || (nowTimestamp > ts1530 && nowTimestamp <= ts2000)) {
//                return;
//            }
//
//            //周五晚上没有夜市
//            if (((now.get(Calendar.DAY_OF_WEEK) == 6) && (nowTimestamp > ts2000 && nowTimestamp < ts2359)) || (now.get(Calendar.DAY_OF_WEEK) == 7) || (now.get(Calendar.DAY_OF_WEEK) == 1)) {
//                return;
//            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }


//        Document doc = JsoupConnectUrl.httpGet(propertyConfigure.getProperty("GET_AU_AG_DOLLAR_AND_TD"));
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        getAgTD(doc,timestamp);
//        getAuTD(doc,timestamp);

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    Document doc = JsoupConnectUrl.httpGet(propertyConfigure.getProperty("GET_AU_AG_DOLLAR_AND_TD"));
//                    getAgTD(doc);
//                    try {
//                        Thread.sleep(Integer.valueOf(propertyConfigure.getProperty("SCAN_AU_TIMEINTERVAL")));
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        Thread thread = new Thread(runnable);
//        thread.start();

    }

//    public static List<AgTD> agTDList = new LinkedList<AgTD>();
//
//    public void getAgTD(Document doc, Timestamp timestamp) {
//        AgTD agTD = new AgTD();
//        try {
//            if (doc != null && doc.select("li[code=AGTD]").hasText()) {
//                if (doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().contains("<")) {
//                    agTD.setNowPrice(doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
//                } else {
//                    agTD.setNowPrice(doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text());
//                }
//
//                agTD.setCode("AGTD");
//
//                if (doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text().contains("<")) {
//                    agTD.setChangePoint(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
//                } else {
//                    agTD.setChangePoint(doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text());
//                }
//
//                if (doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().contains("<")) {
//                    agTD.setChangeRate(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
//                } else {
//                    agTD.setChangeRate(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text());
//                }
//
//                if (doc.select("li[code=AGTD]").select(".hq_open.black").text().contains("<")) {
//                    agTD.setOpenPrice(doc.select("li[code=AGTD]").select(".hq_open.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_open.black").text().indexOf("<") - 1));
//                } else {
//                    agTD.setOpenPrice(doc.select("li[code=AGTD]").select(".hq_open.black").text());
//                }
//
//                if (doc.select("li[code=AGTD]").select(".hq_height.black").text().contains("<")) {
//                    agTD.setHighPrice(doc.select("li[code=AGTD]").select(".hq_height.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_height.black").text().indexOf("<") - 1));
//                } else {
//                    agTD.setHighPrice(doc.select("li[code=AGTD]").select(".hq_height.black").text());
//                }
//
////                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
//                if (doc.select("li[code=AGTD]").select(".hq_low.black").text().contains("<")) {
//                    agTD.setLowPrice(doc.select("li[code=AGTD]").select(".hq_low.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_low.black").text().indexOf("<") - 1));
//                } else {
//                    agTD.setLowPrice(doc.select("li[code=AGTD]").select(".hq_low.black").text());
//                }
//
////                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
//                if (doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().contains("<")) {
//                    agTD.setYesterdayClosePrice(doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
//                } else {
//                    agTD.setYesterdayClosePrice(doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text());
//                }
//
//                agTD.setTime(timestamp);
//
//                if(agTDList.size() == 1){
//                    agTDList.clear();
//                }
//                agTDList.add(agTD);
//
//                int flag = agTDDao.insert(agTD);
//                if (flag > 0) {
////                    System.out.println("插入该条AgTD成功:" + agTD.toString());
//                } else {
//                    System.err.println("插入该条AgTD失败:" + agTD.toString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//    }
//
//    public static List<AuTD> auTDList = new LinkedList<AuTD>();
//
//    @Autowired
//    private AuTDDao auTDDao;
//    private void getAuTD(Document doc, Timestamp timestamp) {
//        AuTD auTD = new AuTD();
//        try {
//            if (doc != null && doc.select("li[code=AUTD]").hasText()) {
//                if (doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().contains("<")) {
//                    auTD.setNowPrice(doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
//                } else {
//                    auTD.setNowPrice(doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text());
//                }
//
//                auTD.setCode("AUTD");
//
//                if (doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text().contains("<")) {
//                    auTD.setChangePoint(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
//                } else {
//                    auTD.setChangePoint(doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text());
//                }
//
//                if (doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().contains("<")) {
//                    auTD.setChangeRate(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
//                } else {
//                    auTD.setChangeRate(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text());
//                }
//
//                if (doc.select("li[code=AUTD]").select(".hq_open.black").text().contains("<")) {
//                    auTD.setOpenPrice(doc.select("li[code=AUTD]").select(".hq_open.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_open.black").text().indexOf("<") - 1));
//                } else {
//                    auTD.setOpenPrice(doc.select("li[code=AUTD]").select(".hq_open.black").text());
//                }
//
//                if (doc.select("li[code=AUTD]").select(".hq_height.black").text().contains("<")) {
//                    auTD.setHighPrice(doc.select("li[code=AUTD]").select(".hq_height.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_height.black").text().indexOf("<") - 1));
//                } else {
//                    auTD.setHighPrice(doc.select("li[code=AUTD]").select(".hq_height.black").text());
//                }
//
////                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
//                if (doc.select("li[code=AUTD]").select(".hq_low.black").text().contains("<")) {
//                    auTD.setLowPrice(doc.select("li[code=AUTD]").select(".hq_low.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_low.black").text().indexOf("<") - 1));
//                } else {
//                    auTD.setLowPrice(doc.select("li[code=AUTD]").select(".hq_low.black").text());
//                }
//
////                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
//                if (doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().contains("<")) {
//                    auTD.setYesterdayClosePrice(doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
//                } else {
//                    auTD.setYesterdayClosePrice(doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text());
//                }
//
//                auTD.setTime(timestamp);
//
//                if(auTDList.size()==1){
//                    auTDList.clear();
//                }
//                auTDList.add(auTD);
//
//                int flag = auTDDao.insert(auTD);
//                if (flag > 0) {
////                    System.out.println("插入该条AuTD成功:" + auTD.toString());
//                } else {
//                    System.out.println("插入该条AuTD失败:" + auTD.toString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//    }
}
