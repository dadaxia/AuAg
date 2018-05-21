package com.gold.cn.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping("/index")
    public String index(){
        logger.info("-------hello，请求到了首页---------");
        return "index";
    }

    @RequestMapping("/news")
    public String news(){
        logger.info("请求到了新闻页面");
        return "index_chart";
    }

    @RequestMapping("/auindex")
    public String au(ModelMap model){
        model.addAttribute("message","hello");
        logger.info("转到现货黄金页面");
        return "AuIndex";
    }

    /**
     * 动态的现货黄金
     * @param model
     * @return
     */
    @RequestMapping("/au")
    public String auIndex(ModelMap model){
        System.out.println("--------请求到现货黄金页面-------------");
        model.addAttribute("title","现货黄金");
        model.addAttribute("type","au");
        model.addAttribute("unit","（美元/盎司）");
        return "dynamic";
    }

    @RequestMapping("demo")
    public String demo(){
        logger.info("demo001");
        return "demo";
    }

    @RequestMapping("agtd")
    public String agTD(){
        System.out.println("--------请求到白银T+D页面-------------");
        return "agtd";
    }

    @RequestMapping("autd")
    public String auTD(Model model){
        System.out.println("--------请求到黄金T+D页面-------------");
        model.addAttribute("title","黄金T+D");
        model.addAttribute("type","autd");
        model.addAttribute("unit","(元/千克)");
        return "dynamic";
    }

    @RequestMapping("usd")
    public String usd(Model model){
        System.out.println("--------请求到美元指数页面-------------");
        model.addAttribute("title","美元指数");
        model.addAttribute("type","usd");
        model.addAttribute("unit"," ");
        return "dynamic";
    }

    @RequestMapping("ag")
    public String ag(Model model){
        System.out.println("--------请求到现货白银页面-------------");
        model.addAttribute("title","现货白银");
        model.addAttribute("type","ag");
        model.addAttribute("unit","（美元/盎司）");
        return "dynamic";
    }

    @RequestMapping("agcny")
    public String agcny(Model model){
        System.out.println("--------请求到纸白银页面-------------");
        model.addAttribute("title","纸白银");
        model.addAttribute("type","agcny");
        model.addAttribute("unit","（元/克）");
        return "dynamic";
    }

    @RequestMapping("aucny")
    public String aucny(Model model){
        System.out.println("--------请求到纸黄金页面-------------");
        model.addAttribute("title","纸黄金");
        model.addAttribute("type","aucny");
        model.addAttribute("unit","（元/克）");
        return "dynamic";
    }

    @RequestMapping("auusd")
    public String auusd(Model model){
        System.out.println("--------请求到现货黄金与美元指数相关系数页面-------------");
        model.addAttribute("title","现货黄金与美元指数相关系数");
        model.addAttribute("type","auusd");
        model.addAttribute("unit","");
        return "dynamic";
    }

}
