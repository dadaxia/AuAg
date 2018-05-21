package com.gold.cn.controller;

import com.gold.cn.model.Au;
import com.gold.cn.model.AuAndUSDRelation;
import com.gold.cn.model.USD;
import com.gold.cn.service.impl.AuServiceImpl;
import com.gold.cn.service.impl.USDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/auusd")
public class AuAndUSDRelationController {
    @Autowired
    private AuServiceImpl auService;
    private USDServiceImpl usdService;

    @RequestMapping(value = "/getLatestData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Au> getLatestAuData(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        String str = sdf.format(todayStart.getTime()).substring(0,sdf.format(todayStart.getTime()).indexOf(" "))+" 00:00:00";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<Au> auList = auService.getLatestAuData(timestamp.valueOf(str));
        List<USD> usdList = usdService.getLatestUSDData(timestamp.valueOf(str));

        Map<Timestamp,String> auMap = new LinkedHashMap<Timestamp,String>();
        Map<Timestamp,String> usdMap = new LinkedHashMap<Timestamp,String>();

        if(usdList != null && usdList.size() > 0){
            for (USD usd : usdList){
                usdMap.put(usd.getTime(),usd.getNowPrice());
            }
        }
        if(auList != null && auList.size() > 0){
            for (Au au : auList){
                auMap.put(au.getTime(),au.getNowPrice());
                if(usdMap.get(au.getTime()) != null){
                    AuAndUSDRelation auAndUSDRelation = new AuAndUSDRelation();
                    auAndUSDRelation.setAuPrice(au.getNowPrice());
                    auAndUSDRelation.setUsdValue(usdMap.get(au.getTime()));
//                    auAndUSDRelation.setRate(Float.valueOf());
                }else{
                    System.out.println("-----------------------没有找到该事件点的美元指数："+au.getTime());
                }
            }
        }




        return auService.getLatestAuData(timestamp.valueOf(str));
    }
}
