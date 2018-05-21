package com.gold.cn.controller;

import com.gold.cn.dao.NewsDao;
import com.gold.cn.model.News;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/news")
public class NewsController  {

    Logger logger = Logger.getLogger(NewsController.class);

    @Autowired
    private NewsDao newsDao;

    @RequestMapping(value = "/getRecentNews", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<News> getRecentNews(){

        logger.info("get recent news:"+newsDao.getFirstTwentyNewsTitle());
        return newsDao.getLatestTwentyNews();
    }
}
