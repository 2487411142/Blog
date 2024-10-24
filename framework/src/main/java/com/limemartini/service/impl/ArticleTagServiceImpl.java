package com.limemartini.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.domain.entity.ArticleTag;
import com.limemartini.mapper.ArticleTagMapper;
import com.limemartini.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2024-06-24 20:25:13
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
