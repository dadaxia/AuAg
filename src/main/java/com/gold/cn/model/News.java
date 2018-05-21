package com.gold.cn.model;

import java.sql.Timestamp;

/**
 * 新闻消息
 */
public class News {
    private long id;
    private String newsId;
    private String autoId;
    private String title;
    private String content;
    private Timestamp date;
    private String type;
    private String importance;
    private String source;
    private String operation;
    private String before;
    private String forecast;
    private String reality;
    private String time;//对应news里的date
    private String predictTime;
    private String code;
    private String state;//国家
    private int effectType;
    private String effect;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNewsId() {
        return newsId;
    }
    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
    public String getAutoId() {
        return autoId;
    }
    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getImportance() {
        return importance;
    }
    public void setImportance(String importance) {
        this.importance = importance;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getBefore() {
        return before;
    }
    public void setBefore(String before) {
        this.before = before;
    }
    public String getForecast() {
        return forecast;
    }
    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
    public String getReality() {
        return reality;
    }
    public void setReality(String reality) {
        this.reality = reality;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPredictTime() {
        return predictTime;
    }
    public void setPredictTime(String predictTime) {
        this.predictTime = predictTime;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getEffectType() {
        return effectType;
    }
    public void setEffectType(int effectType) {
        this.effectType = effectType;
    }
    public String getEffect() {
        return effect;
    }
    public void setEffect(String effect) {
        this.effect = effect;
    }


    public String toString() {
        return "id:"+id+" autoId:"+autoId+" title:"+title+" content:"+content+" date:"+date+" type:"+type+" importance:"+importance+" source:"+source+" operation:"+operation
                +" before:"+before+" forecast:"+forecast+" reality:"+reality+" time:"+time+" predictTime:"+predictTime+" state:"+state+" code:"+code+" effect:"+effect+" effectType:"+effectType;
    }
}
