package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fyq.common.constant.ProductConstant;
import com.fyq.shallowMall.product.entity.AttrAttrgroupRelationEntity;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import com.fyq.shallowMall.product.entity.ProductAttrValueEntity;
import com.fyq.shallowMall.product.service.AttrAttrgroupRelationService;
import com.fyq.shallowMall.product.service.AttrGroupService;
import com.fyq.shallowMall.product.service.CategoryService;
import com.fyq.shallowMall.product.vo.AttrRespVo;
import com.fyq.shallowMall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.product.dao.AttrDao;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, LambdaQueryWrapper<AttrEntity> wrapper) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String attrType) {

        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        if(catalogId != 0){
            wrapper.eq(AttrEntity::getCatalogId, catalogId);
        }

        wrapper.eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getMsg().equalsIgnoreCase(attrType)?
                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():
                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(i -> i.like(AttrEntity::getAttrName, key)
                    .or()
                    .eq(AttrEntity::getAttrId, key));
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> attrEntityList = page.getRecords();

        List<AttrRespVo> attrRespVoList = attrEntityList.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //获取组Id
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationWrapper = new LambdaQueryWrapper<>();
            attrAttrgroupRelationWrapper.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getOne(attrAttrgroupRelationWrapper, false);
            if(attrAttrgroupRelationEntity != null){
                Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
                LambdaQueryWrapper<AttrGroupEntity> attrGroupWrapper = new LambdaQueryWrapper<>();
                attrGroupWrapper.eq(AttrGroupEntity::getAttrGroupId,attrGroupId);
                AttrGroupEntity attrGroupEntity = attrGroupService.getOne(attrGroupWrapper);
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            // 获取分类名
            Long[] catalogPath = categoryService.getCatalogPath(attrEntity.getCatalogId());
            String catelogNames = Arrays.stream(catalogPath)
                    .map(catalogPathId -> categoryService.getById(catalogPathId).getName())
                    .collect(Collectors.joining("/"));
            attrRespVo.setCatalogName(catelogNames);

            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(attrRespVoList);

        return pageUtils;
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);

        //如果有属性分组信息则保存关联关系
        if(attr.getAttrGroupId() != null && attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelation = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelation.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationService.save(attrAttrgroupRelation);
        }
    }

    @Override
    public AttrRespVo getAttrRespVo(Long attrId) {
        //获取属性基本信息
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,  attrRespVo);

        //获取分类路径
        Long[] catalogPath = categoryService.getCatalogPath(attrEntity.getCatalogId());;
        attrRespVo.setCatalogPath(catalogPath);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            //获取组Id
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationWrapper = new LambdaQueryWrapper<>();
            attrAttrgroupRelationWrapper.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getOne(attrAttrgroupRelationWrapper, false);
            if(attrAttrgroupRelationEntity != null) {
                Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
                attrRespVo.setAttrGroupId(attrGroupId);
            }
        }

        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttrCascade(AttrVo attrVo) {
        AttrEntity attr = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attr);
        //更新attr，主要是更新catalogId
        this.updateById(attr);

        //attrGroupId, catalogId
        //前端修改了catalogId，如果没有选择attrGroupId，传来的attrGroupId是''，就需要级联删除关联表；如果有内容，就修改或添加关联表
        //逻辑增加：因为前端将base修改为sale的时候可以将attrGroupId传到后端，这时候如果类型为sale，需要删除该attrId对应的关联表，而不是进行修改
        if(ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode() == attrVo.getAttrType()){
            attrAttrgroupRelationService.deleteRelation(attr.getAttrId());
        }else{
            //删除--新增或修改关联表
            if(attrVo.getAttrGroupId() == null){
                attrAttrgroupRelationService.deleteRelation(attr.getAttrId());
            }else{
                LambdaUpdateWrapper<AttrAttrgroupRelationEntity> wrapper1 = new LambdaUpdateWrapper<>();
                wrapper1.eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId());
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
                attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
                attrAttrgroupRelationService.saveOrUpdate(attrAttrgroupRelationEntity, wrapper1);
            }
        }

    }

    @Override
    public List<AttrEntity> getSearchAttrs(List<Long> attrIds) {
        return this.list(new LambdaQueryWrapper<AttrEntity>()
                .in(AttrEntity::getAttrId, attrIds)
                .eq(AttrEntity::getSearchType, 1));
    }
}