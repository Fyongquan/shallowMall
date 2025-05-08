package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fyq.shallowMall.product.entity.AttrAttrgroupRelationEntity;
import com.fyq.shallowMall.product.service.AttrAttrgroupRelationService;
import com.fyq.shallowMall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.product.dao.AttrDao;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(i -> i.like(AttrEntity::getAttrName, key)
                .or()
                .eq(AttrEntity::getAttrId, key));
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);

        //保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelation = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelation.setAttrId(attrEntity.getAttrId());
        attrAttrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationService.save(attrAttrgroupRelation);
    }

}