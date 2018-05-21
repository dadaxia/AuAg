package com.gold.cn.task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gold.cn.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gold.cn.dao.AgTDDao;
import com.gold.cn.dao.NewsDao;
import com.gold.cn.model.News;

@Component
public class ScanNewsSchedule {

    Logger log = Logger.getLogger(ScanNewsSchedule.class);

    @Autowired
    private PropertyConfigure propertyConfigure;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private AgTDDao agTDDao;

    // 存储数据库中最后存入的100条新闻title
    List<String> lastTitle;
    Timestamp timestamp;
    int number = 0;

    public ScanNewsSchedule() {
        log.info("******************************ScanNewSchedule构造器**************************");
        lastTitle = new ArrayList<String>();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Scheduled(cron = "0/1 * * * * ? ") // 间隔1秒执行
    public void taskCycle() {
//        log.debug("*****************扫描消息******************");
        //从第一黄金网获取的消息
        List<News> newsListFromDiYiHuangJin = FindNewsFromDiYiHuangJinWang.getLastestNews(propertyConfigure.getProperty("GET_NEWS_URL_DIYIHUANGJIN"));
//        System.out.println("第一黄金网消息："+newsListFromDiYiHuangJin);
        //从口袋贵金属获取的新闻
//        System.out.println("获取口袋新闻："+propertyConfigure.getProperty("GET_NEWW_URL_KOUDAI"));
        List<News> newsListFromKouDai = FindNewsFromKouDai.getLatestNews(propertyConfigure.getProperty("GET_NEWW_URL_KOUDAI"));
//        System.out.println("口袋贵金属消息："+newsListFromKouDai);

        //服务器重启获取数据库中最近100条新闻
        if (lastTitle.size() == 0) {
            lastTitle.addAll(newsDao.getFirstTwentyNewsTitle());
        }

        compareNews(newsListFromDiYiHuangJin);

        compareNews(newsListFromKouDai);

        if (lastTitle.size() >= 200) {
            lastTitle = lastTitle.subList(0, 100);
        }
    }

    //比对两条新闻的相似度，相似度低于95%的算作是两条不同的新闻
    public void compareNews(List<News> newsList){
        for(News news : newsList){
            for(int i=0;i<lastTitle.size();i++){
                if(GetSimilarityRatio.getSimilarityRatio(news.getTitle(),lastTitle.get(i)) < 0.85){//相似度小于90%就判断为两条不同的新闻
                    if(i==(lastTitle.size()-1)){
                        lastTitle.add(news.getTitle());
                        insertNews(news);
                        log.info("**********************************"+number+lastTitle.size());
                        number++;
                    }
                }else{
                    log.info("这两条新闻的相似度大于等于95%：(1)."+news.getTitle()+" (2)."+lastTitle.get(i));
                    break;
                }
            }
        }
    }


    /**
     * 将新消息存入数据库
     * @param newsInfo
     */
    @SuppressWarnings("static-access")
    public int insertNews(News newsInfo) {
        // 插入到数据库
        int flag = 0;
        try {
            flag = newsDao.insertNews(newsInfo);
            if (flag > 0) {
                log.info("插入该条新闻成功:" + newsInfo.getAutoId()+"  "+newsInfo);
            } else {
                log.info("插入该条新闻失败:" + newsInfo.getAutoId()+"  "+newsInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
