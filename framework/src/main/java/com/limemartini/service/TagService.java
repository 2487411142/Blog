package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.TagListDto;
import com.limemartini.domain.entity.Tag;
import com.limemartini.domain.vo.BackgroundTagVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-06-18 19:51:04
 */
public interface TagService extends IService<Tag> {

    PageVo listInPage(Integer pageNum, Integer pageSize, TagListDto dto);

    void addNewTag(Tag tag);

    ResponseResult delete(List<Long> id);

    TagVo get(long id);

    void modify(Tag tag);

    List<BackgroundTagVo> listAll();
}

