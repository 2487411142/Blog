package com.limemartini.utils;

import com.limemartini.domain.entity.Article;
import com.limemartini.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        // create target object
        V target = null;
        try {
            target = clazz.newInstance();
            // copy fields
            BeanUtils.copyProperties(source, target);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    public static <O,V> List<V> copyBeanList(List<O> sources, Class<V> clazz){
        return sources.stream()
                .map(source -> copyBean(source, clazz))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Article a = new Article();
        a.setId(1L);
        a.setTitle("aa");

        Article b= new Article();
        b.setId(2L);
        b.setTitle("bb");

        List<Article> articles = new ArrayList<>();
        articles.add(a);
        articles.add(b);

        List<HotArticleVo> vos = copyBeanList(articles, HotArticleVo.class);
        System.out.println(vos);
    }
}
