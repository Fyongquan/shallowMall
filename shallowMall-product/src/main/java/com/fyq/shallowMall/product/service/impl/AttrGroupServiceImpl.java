package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.constant.ProductConstant;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.dao.AttrGroupDao;
import com.fyq.shallowMall.product.entity.AttrAttrgroupRelationEntity;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import com.fyq.shallowMall.product.service.AttrAttrgroupRelationService;
import com.fyq.shallowMall.product.service.AttrGroupService;
import com.fyq.shallowMall.product.service.AttrService;
import com.fyq.shallowMall.product.vo.AttrGroupRelationVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据分类id查询分组和属性
     * @param params
     * @param catalogId
     * @return
     */
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

    /**
     * 查询属性分组关联属性
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getAttrRelation(Long attrGroupId) {

        //根据attrGroupId先去关联表中找到List<Long> attrIds，然后去attr表中查询出对应的属性List<AttrEntity>
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId);
        List<AttrAttrgroupRelationEntity> relationEntityList = relationService.list(wrapper);
        List<AttrEntity> attrList = null;

        if(relationEntityList != null){
            List<Long> attrIds = relationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            if(!attrIds.isEmpty()){
                attrList = (List<AttrEntity>) attrService.listByIds(attrIds);
            }
        }
        return attrList;

    }

    /**
     * 批量删除关联关系
     * @param relationEntities
     */
    @Transactional
    @Override
    public void removeRelationBatch(List<AttrGroupRelationVo> relationEntities) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(wq -> {
            for(AttrGroupRelationVo relationEntity : relationEntities){
                wq.or(i -> i.eq(AttrAttrgroupRelationEntity::getAttrId, relationEntity.getAttrId())
                        .eq(AttrAttrgroupRelationEntity::getAttrGroupId, relationEntity.getAttrGroupId()));
            }
        });
        relationService.remove(wrapper);
    }

    /**
     * 查询属性分组没有关联的属性
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils queryNoattrPage(Map<String, Object> params, Long attrGroupId) {
        // 获取关联表的所有属性ID
//        LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationQueryWrapper = new LambdaQueryWrapper<>();
//        relationQueryWrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId);

        List<AttrAttrgroupRelationEntity> relationEntities = relationService.list();

        List<Long> attrIds = relationEntities.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());

        // 构建排除这些属性的查询条件,还需要排除所有的销售属性
        LambdaQueryWrapper<AttrEntity> attrQueryWrappe = new LambdaQueryWrapper<AttrEntity>().ne(AttrEntity::getAttrType,
                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if(!attrIds.isEmpty()){
            attrQueryWrappe.notIn(AttrEntity::getAttrId, attrIds);
        }

        return attrService.queryPage(params, attrQueryWrappe);
    }

    @Override
    public void saveBatch(List<AttrGroupRelationVo> relationEntities) {
        List<AttrAttrgroupRelationEntity> relationEntities1 = relationEntities.stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationService.saveBatch(relationEntities1);
    }
}