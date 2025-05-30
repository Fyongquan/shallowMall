package com.fyq.shallowMall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.entity.ProductAttrValueEntity;
import com.fyq.shallowMall.product.vo.AttrRespVo;
import com.fyq.shallowMall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    AttrRespVo getAttrRespVo(Long attrId);

    void updateAttrCascade(AttrVo attrRespVo);

    PageUtils queryPage(Map<String, Object> params, LambdaQueryWrapper<AttrEntity> wrapper);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String attrType);

    List<AttrEntity> getSearchAttrs(List<Long> attrIds);
}

