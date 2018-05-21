package com.gold.cn.model;

import java.sql.Timestamp;

public class Au {
    private long id;
    private String code;
    private String highPrice;
    private String lowPrice;
    private String nowPrice;
    private String changePoint;
    private String changeRate;
    private String openPrice;
    private String yesterdayClosePrice;
    private Timestamp time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getChangePoint() {
        return changePoint;
    }

    public void setChangePoint(String changePoint) {
        this.changePoint = changePoint;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(String yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String toString(){
        return "id:"+id+" code:"+code+" highPrice:"+highPrice+" lowPrice:"+lowPrice+" nowPrice:"+nowPrice+" changePoint:"+changePoint
                +" changeRate:"+changeRate+" openPrice:"+openPrice+" yesterdayClosePrice:"+yesterdayClosePrice+" time:"+time;
    }
}
