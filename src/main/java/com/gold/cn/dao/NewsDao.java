package com.gold.cn.dao;

import com.gold.cn.model.News;

import java.sql.Timestamp;
import java.util.List;

public interface NewsDao {
    int insertNews(News news);

    int insertNewsBatch(List<News> newsList);

    List<News> getAllNews();

    News getNewsByAutoId(String autoId);

    List<News> getNewsByTime(Timestamp timestamp);

    int deleteNewsByAutoId(String autoId);

    /**
     * 获取前20条新闻的title
     * @return
     */
    List<String> getFirstTwentyNewsTitle();

    /**
     * 获取最近20条新闻
     * @return
     */
    List<News> getLatestTwentyNews();
}
