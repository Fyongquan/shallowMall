package com.fyq.shallowMall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.vo.AttrVo;

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

    PageUtils queryPage(Map<String, Object> params, Long catalogId);

    void saveAttr(AttrVo attr);
}

