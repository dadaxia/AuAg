package com.gold.cn.util;

import com.gold.cn.model.News;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FindNewsFromKouDai {

    @Autowired
    private static PropertyConfigure propertyConfigure;

    public static List<News> getLatestNews(String url){
        List<News> newsList = new ArrayList<News>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        StringBuilder news = HttpUrlConnection.post(url);
        String str = news.substring(news.indexOf("{\"id\""), news.lastIndexOf("]"));

        String[] newsArray = str.split("},");
        for (int i = 0; i < newsArray.length; i++) {
            News newsInfo = new News();
            JSONObject json = JSONObject.fromObject(newsArray[i] + "}");

            if (json.containsKey("id"))
                newsInfo.setNewsId(json.getString("id"));
            else
                newsInfo.setNewsId(null);
            if (json.containsKey("autoid"))
                newsInfo.setAutoId(json.getString("autoid"));
            else
                newsInfo.setAutoId(json.getString("id")+"_koudai");
            if (json.containsKey("title"))
                newsInfo.setTitle(json.getString("context"));
            else
                newsInfo.setTitle(json.getString("context"));
            if (json.containsKey("content"))
                newsInfo.setContent(json.getString("content"));
            else
                newsInfo.setContent(null);
            if (json.containsKey("" +
                    ""))
                newsInfo.setDate(new Timestamp(Long.valueOf(json.getString("time"))));
            else
                newsInfo.setDate(timestamp);

            newsInfo.setType("1");
            if (json.containsKey("importance"))
                newsInfo.setImportance(json.getString("importance"));
            else{
                if(json.get("color") != null && json.get("color").toString().equals("红色")){
                    newsInfo.setImportance("高");
                }else{
                    newsInfo.setImportance("低");
                }

            }

            if (json.containsKey("type"))
                newsInfo.setSource(json.getString("type"));
            else
                newsInfo.setSource(null);
            if (json.containsKey("operation"))
                newsInfo.setOperation(json.getString("operation"));
            else
                newsInfo.setOperation(null);
            if (json.containsKey("before"))
                newsInfo.setBefore(json.getString("before"));
            else
                newsInfo.setBefore(null);
            if (json.containsKey("forecast"))
                newsInfo.setForecast(json.getString("forecast"));
            else
                newsInfo.setForecast(null);
            if (json.containsKey("reality"))
                newsInfo.setReality(json.getString("reality"));
            else
                newsInfo.setReality(null);
            if (json.containsKey("date"))
                newsInfo.setTime(json.getString("date"));
            else
                newsInfo.setTime(null);
            if (json.containsKey("predictTime"))
                newsInfo.setPredictTime(json.getString("predictTime"));
            else
                newsInfo.setPredictTime(null);
            if (json.containsKey("code"))
                newsInfo.setCode(json.getString("code"));
            else
                newsInfo.setCode(null);
            if (json.containsKey("state"))
                newsInfo.setState(json.getString("state"));
            else
                newsInfo.setState(null);
            if (json.containsKey("effect"))
                newsInfo.setEffect(json.getString("effect"));
            else
                newsInfo.setEffect(null);
            if (json.containsKey("effectype"))
                if (json.getString("effectype") != null)
                    newsInfo.setEffectType(Integer.valueOf(json.getString("effecttype")));
                else
                    newsInfo.setEffectType(0);

            newsList.add(newsInfo);
        }
        return newsList;
    }
}
