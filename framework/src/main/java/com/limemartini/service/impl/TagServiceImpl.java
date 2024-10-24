package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.TagListDto;
import com.limemartini.domain.entity.Tag;
import com.limemartini.domain.vo.BackgroundTagVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.domain.vo.TagVo;
import com.limemartini.mapper.TagMapper;
import com.limemartini.service.TagService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-06-18 19:51:04
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public PageVo listInPage(Integer pageNum, Integer pageSize, TagListDto dto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getName())){
            queryWrapper.eq(Tag::getName, dto.getName());
        }
        if (StringUtils.hasText(dto.getRemark())){
            queryWrapper.eq(Tag::getRemark, dto.getRemark());
        }
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        return new PageVo(tagVos, page.getTotal());
    }

    @Override
    public void addNewTag(Tag tag) {
        Long userId = SecurityUtils.getUserId();
        Date now = new Date();
        tag.setCreateBy(userId);
        tag.setUpdateBy(userId);
        tag.setCreateTime(now);
        tag.setUpdateTime(now);
        save(tag);
    }

    @Override
    public ResponseResult delete(List<Long> ids) {
        ids.forEach(this::removeById);
        return ResponseResult.okResult();
    }

    @Override
    public TagVo get(long id) {
        Tag tag = getById(id);
        return BeanCopyUtils.copyBean(tag, TagVo.class);
    }

    @Override
    public void modify(Tag tag) {
        tag.setUpdateTime(new Date());
        tag.setUpdateBy(SecurityUtils.getUserId());
        updateById(tag);
    }

    @Override
    public List<BackgroundTagVo> listAll() {
        List<Tag> tags = list();
        return BeanCopyUtils.copyBeanList(tags, BackgroundTagVo.class);
    }
}
