package com.limemartini.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Link;
import com.limemartini.domain.vo.LinkVo;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-06-11 20:50:10
 */
public interface LinkService extends IService<Link> {

    List<LinkVo> getAllLink();

    ResponseResult listLinksInPages(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addNewLink(Link link);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLink(List<Long> ids);

    ResponseResult changeLinkStatus(Link link);
}

