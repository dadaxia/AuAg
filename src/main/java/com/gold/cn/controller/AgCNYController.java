package com.gold.cn.controller;

import com.gold.cn.model.AgCNY;
import com.gold.cn.service.impl.AgCNYServiceImpl;
import com.gold.cn.task.ScanAuAgUSDSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(value = "/agcny")
public class AgCNYController {
    @Autowired
    private AgCNYServiceImpl agCNYService;

    @RequestMapping(value = "/getNowData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AgCNY getData(){
        if(ScanAuAgUSDSchedule.agCNYList != null && ScanAuAgUSDSchedule.agCNYList.size() > 0){
            return ScanAuAgUSDSchedule.agCNYList.get(0);
        }
        return null;
    }

    @RequestMapping(value = "/getLatestData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<AgCNY> getLatestAgData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        String str = sdf.format(todayStart.getTime()).substring(0,sdf.format(todayStart.getTime()).indexOf(" "))+" 00:00:00";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return agCNYService.getLatestAgCNYData(timestamp.valueOf(str));
    }
}
