package com.gold.cn.service.impl;

import com.gold.cn.dao.NewsDao;
import com.gold.cn.model.News;
import com.gold.cn.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequestMapping("/news")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public int insertNews(News news) {
        return newsDao.insertNews(news);
    }

    @Override
    public int insertNewsBatch(List<News> newsList) {
        return newsDao.insertNewsBatch(newsList);
    }

    @Override
    public List<News> getAllNews() {
        return newsDao.getAllNews();
    }

    @Override
    public News getNewsByAutoId(String autoId) {
        return newsDao.getNewsByAutoId(autoId);
    }

    @Override
    public List<News> getNewsByTime(Timestamp timestamp) {
        return newsDao.getNewsByTime(timestamp);
    }

    @Override
    public int deleteNewsByAutoId(String autoId) {
        return newsDao.deleteNewsByAutoId(autoId);
    }

    @Override
    @RequestMapping("/getFirstTwentyNewsTitle")
    public List<String> getFirstTwentyNewsTitle() {
        return newsDao.getFirstTwentyNewsTitle();
    }

    @Override
    public List<News> getLatestTwentyNews() {
        return newsDao.getLatestTwentyNews();
    }
}
