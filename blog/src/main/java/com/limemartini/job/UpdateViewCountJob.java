package com.limemartini.job;

import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.entity.Article;
import com.limemartini.service.ArticleService;
import com.limemartini.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UpdateViewCountJob {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "* 0/10 * * * ?")
    public void testJob(){
        // get view count saved in redis
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_VIEW_COUNT);
        // update into database
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }
}
