package com.gold.cn.task;

import com.gold.cn.dao.*;
import com.gold.cn.model.*;
import com.gold.cn.util.JsoupConnectUrl;
import com.gold.cn.util.PropertyConfigure;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 获取现货黄金、白银、美元指数、纸黄金、纸白银
 */
@Component
public class ScanAuAgUSDSchedule {
    @Autowired
    private PropertyConfigure propertyConfigure;

    public static List<Au> auList = new LinkedList<Au>();
    public static List<Ag> agList = new LinkedList<Ag>();
    public static List<USD> usdList = new LinkedList<USD>();
    public static List<AuCNY> auCNYList = new LinkedList<AuCNY>();
    public static List<AgCNY> agCNYList = new LinkedList<AgCNY>();

    @Scheduled(cron = "0/1 * * * * MON-SAT ")
    public void getAuAgData() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * 以北京时间为基准现货黄金交易时间如下：
         * 夏令交易时间：星期一上午06:00至星期六凌晨04:45，国际节假日休市；
         * 夏令暂停时间：星期二至星期五上午05:00至06:00；
         * 夏令结算时间：星期二至星期六上午05:00；
         * 冬令交易时间：星期一上午07:00至星期六凌晨05:45，国际节假日休市；
         * 冬令暂停时间：星期二至星期五上午06:00至07:00；
         * 冬令结算时间：星期二至星期六上午06:00。
         */

        try {
            long ts0600 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "06:00:00").getTime();

            //运行时间为周一早上6:00至周六早上6:00
            long nowTimestamp = System.currentTimeMillis();
            Document doc = JsoupConnectUrl.httpGet(propertyConfigure.getProperty("GET_AU_AG_DOLLAR_AND_TD"));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //周六早上6：00以后和周日没有交易
            if (((now.get(Calendar.DAY_OF_WEEK) == 7) && (nowTimestamp > ts0600)) || (now.get(Calendar.DAY_OF_WEEK) == 1)) {

            }else{

                getAu(doc,timestamp);
                getAg(doc,timestamp);
                getUSD(doc,timestamp);
                getAuCNY(doc,timestamp);
                getAgCNY(doc,timestamp);
            }

            long ts0230 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "02:30:00").getTime();
            long ts0900 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "09:00:00").getTime();
            long ts1130 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "11:30:00").getTime();
            long ts1330 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "13:30:00").getTime();
            long ts1530 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "15:30:00").getTime();
            long ts2000 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "20:00:00").getTime();
            long ts2359 = sdf.parse(String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH)) + " " + "23:59:00").getTime();

            //T+D运行时间为周一至周五：9:00-11:30 13:30-15:30 20:00-02:00
            if ((nowTimestamp > ts0230 && nowTimestamp < ts0900) || (nowTimestamp > ts1130 && nowTimestamp < ts1330) || (nowTimestamp > ts1530 && nowTimestamp <= ts2000)) {

            }else if (((now.get(Calendar.DAY_OF_WEEK) == 6) && (nowTimestamp > ts2000 && nowTimestamp < ts2359)) || (now.get(Calendar.DAY_OF_WEEK) == 7) || (now.get(Calendar.DAY_OF_WEEK) == 1)) {
                //周五晚上没有夜市
            }else{
                getAgTD(doc,timestamp);
                getAuTD(doc,timestamp);
            }

        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }


    }


    @Autowired
    private AuDao auDao;
    private void getAu(Document doc, Timestamp timestamp){
        Au au = new Au();
        try {
            if(doc != null && doc.select("li[code=XAU]").hasText()){
                if (doc.select("li[code=XAU]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    au.setNowPrice(doc.select("li[code=XAU]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=XAU]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    au.setNowPrice(doc.select("li[code=XAU]").select(".hq_price.cor.data_1.last").text());
                }

                au.setCode("XAU");

                if (doc.select("li[code=XAU]").select(".hq_change.cor.swing").text().contains("<")) {
                    au.setChangePoint(doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=XAU]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    au.setChangePoint(doc.select("li[code=XAU]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    au.setChangeRate(doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    au.setChangeRate(doc.select("li[code=XAU]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=XAU]").select(".hq_open.black").text().contains("<")) {
                    au.setOpenPrice(doc.select("li[code=XAU]").select(".hq_open.black").text().substring(0, doc.select("li[code=XAU]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    au.setOpenPrice(doc.select("li[code=XAU]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=XAU]").select(".hq_height.black").text().contains("<")) {
                    au.setHighPrice(doc.select("li[code=XAU]").select(".hq_height.black").text().substring(0, doc.select("li[code=XAU]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    au.setHighPrice(doc.select("li[code=XAU]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=XAU]").select(".hq_low.black").text().contains("<")) {
                    au.setLowPrice(doc.select("li[code=XAU]").select(".hq_low.black").text().substring(0, doc.select("li[code=XAU]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    au.setLowPrice(doc.select("li[code=XAU]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=XAU]").select(".hq_close.black.lastt").text().contains("<")) {
                    au.setYesterdayClosePrice(doc.select("li[code=XAU]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=XAU]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    au.setYesterdayClosePrice(doc.select("li[code=XAU]").select(".hq_close.black.lastt").text());
                }

                au.setTime(timestamp);

                if(auList.size()==1){
                    auList.clear();
                }
                auList.add(au);
                int flag = auDao.insert(au);
                if(flag >0){
//                    System.out.println("插入该条Au成功:" + au.toString());
                }else{
                    System.out.println("插入该条Au失败:" + au.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
//            log.error("Au:"+au.toString());
        }finally {

        }
    }


    @Autowired
    private USDDao usdDao;

    private void getUSD(Document doc,Timestamp timestamp) {
        USD usd = new USD();
        try {
            if (doc != null && doc.select("li[code=DINIW]").hasText()) {
                if (doc.select("li[code=DINIW]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    usd.setNowPrice(doc.select("li[code=DINIW]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=DINIW]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    usd.setNowPrice(doc.select("li[code=DINIW]").select(".hq_price.cor.data_1.last").text());
                }

                usd.setCode("DINIW");

                if (doc.select("li[code=DINIW]").select(".hq_change.cor.swing").text().contains("<")) {
                    usd.setChangePoint(doc.select("li[code=DINIW]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=DINIW]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    usd.setChangePoint(doc.select("li[code=DINIW]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=DINIW]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    usd.setChangeRate(doc.select("li[code=DINIW]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=DINIW]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    usd.setChangeRate(doc.select("li[code=DINIW]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=DINIW]").select(".hq_open.black").text().contains("<")) {
                    usd.setOpenPrice(doc.select("li[code=DINIW]").select(".hq_open.black").text().substring(0, doc.select("li[code=DINIW]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    usd.setOpenPrice(doc.select("li[code=DINIW]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=DINIW]").select(".hq_height.black").text().contains("<")) {
                    usd.setHighPrice(doc.select("li[code=DINIW]").select(".hq_height.black").text().substring(0, doc.select("li[code=DINIW]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    usd.setHighPrice(doc.select("li[code=DINIW]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=DINIW]").select(".hq_low.black").text().contains("<")) {
                    usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text().substring(0, doc.select("li[code=DINIW]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text().contains("<")) {
                    usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                }

                usd.setTime(timestamp);

                if(usdList.size()==1){
                    usdList.clear();
                }
                usdList.add(usd);

                int flag = usdDao.insert(usd);
                if (flag > 0) {
//                    log.info("插入该条USD成功:" + usd.toString());
                } else {
//                    log.info("插入该条USD失败:" + usd.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Autowired
    private AgDao agDao;

    private void getAg(Document doc,Timestamp timestamp) {
        Ag ag = new Ag();
        try {
            if (doc != null && doc.select("li[code=XAG]").hasText()) {
                if (doc.select("li[code=XAG]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    ag.setNowPrice(doc.select("li[code=XAG]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=XAG]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    ag.setNowPrice(doc.select("li[code=XAG]").select(".hq_price.cor.data_1.last").text());
                }

                ag.setCode("XAG");

                if (doc.select("li[code=XAG]").select(".hq_change.cor.swing").text().contains("<")) {
                    ag.setChangePoint(doc.select("li[code=XAG]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=XAG]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    ag.setChangePoint(doc.select("li[code=XAG]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=XAG]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    ag.setChangeRate(doc.select("li[code=XAG]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=XAG]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    ag.setChangeRate(doc.select("li[code=XAG]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=XAG]").select(".hq_open.black").text().contains("<")) {
                    ag.setOpenPrice(doc.select("li[code=XAG]").select(".hq_open.black").text().substring(0, doc.select("li[code=XAG]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    ag.setOpenPrice(doc.select("li[code=XAG]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=XAG]").select(".hq_height.black").text().contains("<")) {
                    ag.setHighPrice(doc.select("li[code=XAG]").select(".hq_height.black").text().substring(0, doc.select("li[code=XAG]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    ag.setHighPrice(doc.select("li[code=XAG]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=XAG]").select(".hq_low.black").text().contains("<")) {
                    ag.setLowPrice(doc.select("li[code=XAG]").select(".hq_low.black").text().substring(0, doc.select("li[code=XAG]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    ag.setLowPrice(doc.select("li[code=XAG]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=XAG]").select(".hq_close.black.lastt").text().contains("<")) {
                    ag.setYesterdayClosePrice(doc.select("li[code=XAG]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=XAG]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    ag.setYesterdayClosePrice(doc.select("li[code=XAG]").select(".hq_close.black.lastt").text());
                }

                ag.setTime(timestamp);

                if(agList.size()==1){
                    agList.clear();
                }
                agList.add(ag);

                int flag = agDao.insert(ag);
                if (flag > 0) {
//                    log.info("插入该条Ag成功:" + ag.toString());
                } else {
//                    log.info("插入该条Ag失败:" + ag.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Ag:" + ag.toString());
        } finally {

        }
    }

    @Autowired
    private AuCNYDao auCNYDao;

    private void getAuCNY(Document doc,Timestamp timestamp) {
        AuCNY auCNY = new AuCNY();
        try {
            if (doc != null && doc.select("li[code=AUCNY]").hasText()) {
                if (doc.select("li[code=AUCNY]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    auCNY.setNowPrice(doc.select("li[code=AUCNY]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    auCNY.setNowPrice(doc.select("li[code=AUCNY]").select(".hq_price.cor.data_1.last").text());
                }

                auCNY.setCode("AUCNY");

                if (doc.select("li[code=AUCNY]").select(".hq_change.cor.swing").text().contains("<")) {
                    auCNY.setChangePoint(doc.select("li[code=AUCNY]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    auCNY.setChangePoint(doc.select("li[code=AUCNY]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=AUCNY]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    auCNY.setChangeRate(doc.select("li[code=AUCNY]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    auCNY.setChangeRate(doc.select("li[code=AUCNY]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=AUCNY]").select(".hq_open.black").text().contains("<")) {
                    auCNY.setOpenPrice(doc.select("li[code=AUCNY]").select(".hq_open.black").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    auCNY.setOpenPrice(doc.select("li[code=AUCNY]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=AUCNY]").select(".hq_height.black").text().contains("<")) {
                    auCNY.setHighPrice(doc.select("li[code=AUCNY]").select(".hq_height.black").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    auCNY.setHighPrice(doc.select("li[code=AUCNY]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=AUCNY]").select(".hq_low.black").text().contains("<")) {
                    auCNY.setLowPrice(doc.select("li[code=AUCNY]").select(".hq_low.black").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    auCNY.setLowPrice(doc.select("li[code=AUCNY]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=AUCNY]").select(".hq_close.black.lastt").text().contains("<")) {
                    auCNY.setYesterdayClosePrice(doc.select("li[code=AUCNY]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AUCNY]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    auCNY.setYesterdayClosePrice(doc.select("li[code=AUCNY]").select(".hq_close.black.lastt").text());
                }


                auCNY.setTime(timestamp);

                if(auCNYList.size()==1){
                    auCNYList.clear();
                }
                auCNYList.add(auCNY);

                int flag = auCNYDao.insert(auCNY);
                if (flag > 0) {
//                    log.info("插入该条AuCNY成功:" + auCNY.toString());
                } else {
//                    log.info("插入该条AuCNY失败:" + auCNY.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Autowired
    private AgCNYDao agCNYDao;

    private void getAgCNY(Document doc,Timestamp timestamp) {
        AgCNY agCNY = new AgCNY();
        try {
//            Document doc = JsoupConnectUrl.httpGet(propertyConfigure.getProperty("GET_AU_AG_DOLLAR_AND_TD"));
            if (doc != null && doc.select("li[code=AGCNY]").hasText()) {

                if (doc.select("li[code=AGCNY]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    agCNY.setNowPrice(doc.select("li[code=AGCNY]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    agCNY.setNowPrice(doc.select("li[code=AGCNY]").select(".hq_price.cor.data_1.last").text());
                }

                agCNY.setCode("AGCNY");

                if (doc.select("li[code=AGCNY]").select(".hq_change.cor.swing").text().contains("<")) {
                    agCNY.setChangePoint(doc.select("li[code=AGCNY]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    agCNY.setChangePoint(doc.select("li[code=AGCNY]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=AGCNY]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    agCNY.setChangeRate(doc.select("li[code=AGCNY]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    agCNY.setChangeRate(doc.select("li[code=AGCNY]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=AGCNY]").select(".hq_open.black").text().contains("<")) {
                    agCNY.setOpenPrice(doc.select("li[code=AGCNY]").select(".hq_open.black").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    agCNY.setOpenPrice(doc.select("li[code=AGCNY]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=AGCNY]").select(".hq_height.black").text().contains("<")) {
                    agCNY.setHighPrice(doc.select("li[code=AGCNY]").select(".hq_height.black").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    agCNY.setHighPrice(doc.select("li[code=AGCNY]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=AGCNY]").select(".hq_low.black").text().contains("<")) {
                    agCNY.setLowPrice(doc.select("li[code=AGCNY]").select(".hq_low.black").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    agCNY.setLowPrice(doc.select("li[code=AGCNY]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=AGCNY]").select(".hq_close.black.lastt").text().contains("<")) {
                    agCNY.setYesterdayClosePrice(doc.select("li[code=AGCNY]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AGCNY]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    agCNY.setYesterdayClosePrice(doc.select("li[code=AGCNY]").select(".hq_close.black.lastt").text());
                }

                agCNY.setTime(timestamp);

                if(agCNYList.size()==1){
                    agCNYList.clear();
                }
                agCNYList.add(agCNY);

                int flag = agCNYDao.insert(agCNY);
                if (flag > 0) {
//                    log.info("插入该条AgCNY成功:" + agCNY.toString());
                } else {
//                    log.info("插入该条AgCNY失败:" + agCNY.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static List<AgTD> agTDList = new LinkedList<AgTD>();

    @Autowired
    private AgTDDao agTDDao;

    public void getAgTD(Document doc, Timestamp timestamp) {
        AgTD agTD = new AgTD();
        try {
            if (doc != null && doc.select("li[code=AGTD]").hasText()) {
                if (doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    agTD.setNowPrice(doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    agTD.setNowPrice(doc.select("li[code=AGTD]").select(".hq_price.cor.data_1.last").text());
                }

                agTD.setCode("AGTD");

                if (doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text().contains("<")) {
                    agTD.setChangePoint(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    agTD.setChangePoint(doc.select("li[code=AGTD]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    agTD.setChangeRate(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    agTD.setChangeRate(doc.select("li[code=AGTD]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=AGTD]").select(".hq_open.black").text().contains("<")) {
                    agTD.setOpenPrice(doc.select("li[code=AGTD]").select(".hq_open.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    agTD.setOpenPrice(doc.select("li[code=AGTD]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=AGTD]").select(".hq_height.black").text().contains("<")) {
                    agTD.setHighPrice(doc.select("li[code=AGTD]").select(".hq_height.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    agTD.setHighPrice(doc.select("li[code=AGTD]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=AGTD]").select(".hq_low.black").text().contains("<")) {
                    agTD.setLowPrice(doc.select("li[code=AGTD]").select(".hq_low.black").text().substring(0, doc.select("li[code=AGTD]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    agTD.setLowPrice(doc.select("li[code=AGTD]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().contains("<")) {
                    agTD.setYesterdayClosePrice(doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    agTD.setYesterdayClosePrice(doc.select("li[code=AGTD]").select(".hq_close.black.lastt").text());
                }

                agTD.setTime(timestamp);

                if(agTDList.size() == 1){
                    agTDList.clear();
                }
                agTDList.add(agTD);

                int flag = agTDDao.insert(agTD);
                if (flag > 0) {
//                    System.out.println("插入该条AgTD成功:" + agTD.toString());
                } else {
                    System.err.println("插入该条AgTD失败:" + agTD.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static List<AuTD> auTDList = new LinkedList<AuTD>();

    @Autowired
    private AuTDDao auTDDao;
    private void getAuTD(Document doc, Timestamp timestamp) {
        AuTD auTD = new AuTD();
        try {
            if (doc != null && doc.select("li[code=AUTD]").hasText()) {
                if (doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().contains("<")) {
                    auTD.setNowPrice(doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().substring(0, doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text().indexOf("<") - 1));
                } else {
                    auTD.setNowPrice(doc.select("li[code=AUTD]").select(".hq_price.cor.data_1.last").text());
                }

                auTD.setCode("AUTD");

                if (doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text().contains("<")) {
                    auTD.setChangePoint(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text().indexOf("<") - 1));
                } else {
                    auTD.setChangePoint(doc.select("li[code=AUTD]").select(".hq_change.cor.swing").text());
                }

                if (doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().contains("<")) {
                    auTD.setChangeRate(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().substring(0, doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text().indexOf("<") - 1));
                } else {
                    auTD.setChangeRate(doc.select("li[code=AUTD]").select(".hq_range.cor.swingRange").text());
                }

                if (doc.select("li[code=AUTD]").select(".hq_open.black").text().contains("<")) {
                    auTD.setOpenPrice(doc.select("li[code=AUTD]").select(".hq_open.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_open.black").text().indexOf("<") - 1));
                } else {
                    auTD.setOpenPrice(doc.select("li[code=AUTD]").select(".hq_open.black").text());
                }

                if (doc.select("li[code=AUTD]").select(".hq_height.black").text().contains("<")) {
                    auTD.setHighPrice(doc.select("li[code=AUTD]").select(".hq_height.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_height.black").text().indexOf("<") - 1));
                } else {
                    auTD.setHighPrice(doc.select("li[code=AUTD]").select(".hq_height.black").text());
                }

//                usd.setLowPrice(doc.select("li[code=DINIW]").select(".hq_low.black").text());
                if (doc.select("li[code=AUTD]").select(".hq_low.black").text().contains("<")) {
                    auTD.setLowPrice(doc.select("li[code=AUTD]").select(".hq_low.black").text().substring(0, doc.select("li[code=AUTD]").select(".hq_low.black").text().indexOf("<") - 1));
                } else {
                    auTD.setLowPrice(doc.select("li[code=AUTD]").select(".hq_low.black").text());
                }

//                usd.setYesterdayClosePrice(doc.select("li[code=DINIW]").select(".hq_close.black.lastt").text());
                if (doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().contains("<")) {
                    auTD.setYesterdayClosePrice(doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().substring(0, doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text().indexOf("<") - 1));
                } else {
                    auTD.setYesterdayClosePrice(doc.select("li[code=AUTD]").select(".hq_close.black.lastt").text());
                }

                auTD.setTime(timestamp);

                if(auTDList.size()==1){
                    auTDList.clear();
                }
                auTDList.add(auTD);

                int flag = auTDDao.insert(auTD);
                if (flag > 0) {
//                    System.out.println("插入该条AuTD成功:" + auTD.toString());
                } else {
                    System.out.println("插入该条AuTD失败:" + auTD.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
