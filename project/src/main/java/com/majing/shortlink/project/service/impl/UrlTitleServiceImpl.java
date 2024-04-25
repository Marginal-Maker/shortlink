package com.majing.shortlink.project.service.impl;

import com.majing.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author majing
 * @date 2024-04-25 15:12
 * @Description url标题实现层
 */
@Service
public class UrlTitleServiceImpl implements UrlTitleService {
    @SneakyThrows
    @Override
    public String getUrlTitle(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.connect();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        return "ERROR while fetching title";
    }
}
