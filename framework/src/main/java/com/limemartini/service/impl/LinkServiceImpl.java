package com.limemartini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limemartini.constants.SystemConstants;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.entity.Link;
import com.limemartini.domain.vo.BackgroundLinkVo;
import com.limemartini.domain.vo.LinkVo;
import com.limemartini.domain.vo.PageVo;
import com.limemartini.mapper.LinkMapper;
import com.limemartini.service.LinkService;
import com.limemartini.utils.BeanCopyUtils;
import com.limemartini.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-06-11 20:50:11
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public List<LinkVo> getAllLink() {
        // search all links which have status = 0
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // transfer to vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //encapsulate
        return linkVos;
    }

    @Override
    public ResponseResult listLinksInPages(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status);

        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Link> links = page.getRecords();
        List<BackgroundLinkVo> linkVos = BeanCopyUtils.copyBeanList(links, BackgroundLinkVo.class);
        return ResponseResult.okResult(new PageVo(linkVos, page.getTotal()));
    }

    @Override
    public ResponseResult addNewLink(Link link) {
        Long userId = SecurityUtils.getUserId();
        link.setCreateBy(userId);
        link.setUpdateBy(userId);

        Date now = new Date();
        link.setCreateTime(now);
        link.setUpdateTime(now);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        BackgroundLinkVo backgroundLinkVo = BeanCopyUtils.copyBean(link, BackgroundLinkVo.class);
        return ResponseResult.okResult(backgroundLinkVo);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        link.setUpdateBy(SecurityUtils.getUserId());
        link.setUpdateTime(new Date());
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> ids) {
        ids.forEach(this::removeById);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeLinkStatus(Link link) {
        link.setUpdateBy(SecurityUtils.getUserId());
        link.setUpdateTime(new Date());
        updateById(link);
        return ResponseResult.okResult();
    }
}
