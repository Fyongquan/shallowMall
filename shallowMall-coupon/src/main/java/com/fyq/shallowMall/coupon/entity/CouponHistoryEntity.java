package com.fyq.shallowMall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 券领取记录
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@Data
@TableName("sms_coupon_history")
public class CouponHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录ID
	 */
	@TableId
	private Long id;
	/**
	 * 券ID
	 */
	private Long couponId;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 会员昵称
	 */
	private String memberNickName;
	/**
	 * 领取方式[0-赠送 1-主动]
	 */
	private Integer getType;
	/**
	 * 领取时间
	 */
	private Date createTime;
	/**
	 * 使用状态[0-未用 1-已用 2-过期]
	 */
	private Integer useType;
	/**
	 * 核销时间
	 */
	private Date useTime;
	/**
	 * 关联订单
	 */
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderSn;

}
