package com.limemartini.runner;

import com.limemartini.constants.SystemConstants;
import com.limemartini.service.ArticleService;
import com.limemartini.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // search article id, view count
        Map<String, Integer> viewCountMap = articleService.getViewCountMap();
        // save into redis
        redisCache.setCacheMap(SystemConstants.REDIS_VIEW_COUNT, viewCountMap);
    }
}
