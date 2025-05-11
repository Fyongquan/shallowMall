package com.fyq.shallowMall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import com.fyq.shallowMall.product.vo.AttrGroupRelationVo;
import com.fyq.shallowMall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catalogId);

    List<AttrEntity> getAttrRelation(Long attrGroupId);

    void removeRelationBatch(List<AttrGroupRelationVo> relationEntities);

    PageUtils queryNoattrPage(Map<String, Object> params, Long attrGroupId);

    void saveBatch(List<AttrGroupRelationVo> relationEntities);

    List<AttrGroupWithAttrsVo> getAttrGroupsWithAttrsByCatalogId(Long catalogId);
}

