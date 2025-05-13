package com.fyq.shallowMall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:06:23
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPurchaseDetailPage(Map<String, Object> params, Long wareId, Integer status, String key);
}

