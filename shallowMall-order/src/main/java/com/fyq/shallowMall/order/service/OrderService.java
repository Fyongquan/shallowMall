package com.fyq.shallowMall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单主表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:03:57
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

