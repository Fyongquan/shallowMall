package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.dao.AttrGroupDao;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import com.fyq.shallowMall.product.service.AttrGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.imageio.spi.IIOServiceProvider;
import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(i -> i.eq(AttrGroupEntity::getAttrGroupId, key)
                    .or()
                    .like(AttrGroupEntity::getAttrGroupName, key));
        }
        if (catalogId > 0) {
            wrapper.eq(AttrGroupEntity::getCatalogId, catalogId);
        }
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}