package com.fyq.shallowMall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:06:23
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryWareSkuPage(Map<String, Object> params, Long skuId, Long wareId);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}

