package com.gold.cn.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupConnectUrl {
    public static Document httpGet(String url){
        Connection connection = null;
        Document document = null;
        try{
            connection = Jsoup.connect(url);
            connection.header("Accept", "text/html, application/xhtml+xml, */*");
            connection.header("Content-Type", "application/x-www-form-urlencoded");
            connection.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");

            //解析请求结果
            document = connection.get();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null){

            }
        }

        return document;
    }
}
