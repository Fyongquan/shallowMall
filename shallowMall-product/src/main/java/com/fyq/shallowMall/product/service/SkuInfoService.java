package com.fyq.shallowMall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.SkuInfoEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * sku信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catalogId, String key, Long brandId, BigDecimal min, BigDecimal max);
}

