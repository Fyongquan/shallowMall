package com.fyq.shallowMall.coupon.dao;

import com.fyq.shallowMall.coupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
