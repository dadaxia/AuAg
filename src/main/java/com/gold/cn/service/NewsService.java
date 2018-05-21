package com.gold.cn.service;

import com.gold.cn.model.News;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface NewsService {
    int insertNews(News news);

    int insertNewsBatch(List<News> newsList);

    List<News> getAllNews();

    News getNewsByAutoId(String autoId);

    List<News> getNewsByTime(Timestamp timestamp);

    int deleteNewsByAutoId(String autoId);

    List<String> getFirstTwentyNewsTitle();

    List<News> getLatestTwentyNews();

}
