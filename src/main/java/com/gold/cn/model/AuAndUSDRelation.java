package com.gold.cn.model;

import java.sql.Timestamp;

/**
 * 现货黄金与美元指数的相关系数
 */
public class AuAndUSDRelation {
    private String auPrice;
    private String usdValue;
    private float rate;
    private Timestamp time;

    public String getAuPrice() {
        return auPrice;
    }

    public void setAuPrice(String auPrice) {
        this.auPrice = auPrice;
    }

    public String getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(String usdValue) {
        this.usdValue = usdValue;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
