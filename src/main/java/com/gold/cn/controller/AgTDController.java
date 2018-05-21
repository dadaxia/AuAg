package com.gold.cn.controller;

import com.gold.cn.model.AgTD;
import com.gold.cn.service.impl.AgTDServiceImpl;
import com.gold.cn.task.ScanAuAgUSDSchedule;
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

/**
 * 白银T+D
 */
@Controller
@RequestMapping("/agtd")
public class AgTDController {

    @Autowired
    private AgTDServiceImpl agTDService;

    @RequestMapping(value = "/getNowData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AgTD getNowPrice(){
//        System.out.println("-----------获取白银T+D最新数据------------");
        if(ScanAuAgUSDSchedule.agTDList != null && ScanAuAgUSDSchedule.agTDList.size() > 0){
//            System.out.println("-----------获取白银T+D最新数据clear前:"+ ScanAuAgTDSchedule.agTDList.size());
            return ScanAuAgUSDSchedule.agTDList.get(0);
        }
        return null;
    }

    @RequestMapping(value = "/getLatestAgTDData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<AgTD> getLatestAuData(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        String str = sdf.format(todayStart.getTime()).substring(0,sdf.format(todayStart.getTime()).indexOf(" "))+" 00:00:00";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return agTDService.getLatestAgTDData(timestamp.valueOf(str));
    }

    @RequestMapping(value = "/getNewestAgTDData", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<AgTD> getNewestAuData(@RequestParam(value="id") long id){

        return agTDService.getNewestAgTDData(id);
    }
}
