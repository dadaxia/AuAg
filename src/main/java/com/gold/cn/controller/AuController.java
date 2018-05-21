package com.gold.cn.controller;

import com.gold.cn.model.Au;
import com.gold.cn.service.impl.AuServiceImpl;
import com.gold.cn.task.ScanAuAgUSDSchedule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(value = "/au")
public class AuController {

    Logger logger = Logger.getLogger(AuController.class);
    @Autowired
    private AuServiceImpl auService;

    @RequestMapping(value = "/getNowData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Au getData(){
        if(ScanAuAgUSDSchedule.auList != null && ScanAuAgUSDSchedule.auList.size() > 0){
//            System.out.println("-----------获取现货换金最新数据clear前:"+ ScanAuAgUSDSchedule.auList.size());

            return ScanAuAgUSDSchedule.auList.get(0);
        }
        return null;
    }

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

        return auService.getLatestAuData(timestamp.valueOf(str));
    }

    @RequestMapping(value = "/getNewestAuData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<Au> getNewestAuData(@RequestParam(value="id") long id){

        return auService.getNewestAuData(id);
    }
}
