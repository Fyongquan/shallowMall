package com.fyq.shallowMall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 优惠券主表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@Data
@TableName("sms_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 券ID
	 */
	@TableId
	private Long id;
	/**
	 * 类型[0-全场 1-会员 2-购物 3-注册]
	 */
	private Integer couponType;
	/**
	 * 图片路径
	 */
	private String couponImg;
	/**
	 * 名称
	 */
	private String couponName;
	/**
	 * 总数量
	 */
	private Integer num;
	/**
	 * 面值
	 */
	private BigDecimal amount;
	/**
	 * 每人限领
	 */
	private Integer perLimit;
	/**
	 * 使用门槛
	 */
	private BigDecimal minPoint;
	/**
	 * 生效时间
	 */
	private Date startTime;
	/**
	 * 失效时间
	 */
	private Date endTime;
	/**
	 * 适用范围[0-全场 1-类目 2-商品]
	 */
	private Integer useType;
	/**
	 * 运营备注
	 */
	private String note;
	/**
	 * 发行量
	 */
	private Integer publishCount;
	/**
	 * 已用量
	 */
	private Integer useCount;
	/**
	 * 领取量
	 */
	private Integer receiveCount;
	/**
	 * 领取开始
	 */
	private Date enableStartTime;
	/**
	 * 领取截止
	 */
	private Date enableEndTime;
	/**
	 * 优惠码
	 */
	private String code;
	/**
	 * 领取等级
	 */
	private Integer memberLevel;
	/**
	 * 发布状态
	 */
	private Integer publish;

}
