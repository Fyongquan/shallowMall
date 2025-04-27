package com.fyq.shallowMall.order.dao;

import com.fyq.shallowMall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单主表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:03:57
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
