package com.fyq.shallowMall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单主表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:03:57
 */
@Data
@TableName("oms_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Long id;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 优惠券ID
	 */
	private Long couponId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 用户名
	 */
	private String memberUsername;
	/**
	 * 订单总额
	 */
	private BigDecimal totalAmount;
	/**
	 * 应付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 运费
	 */
	private BigDecimal freightAmount;
	/**
	 * 促销金额
	 */
	private BigDecimal promotionAmount;
	/**
	 * 积分抵扣
	 */
	private BigDecimal integrationAmount;
	/**
	 * 优惠券抵扣
	 */
	private BigDecimal couponAmount;
	/**
	 * 后台调价
	 */
	private BigDecimal discountAmount;
	/**
	 * 支付方式(1支付宝 2微信 3银联 4货到付款)
	 */
	private Integer payType;
	/**
	 * 订单来源(0-PC 1-App)
	 */
	private Integer sourceType;
	/**
	 * 订单状态(0待付款 1待发货 2已发货 3已完成 4已关闭 5无效)
	 */
	private Integer status;
	/**
	 * 物流公司
	 */
	private String deliveryCompany;
	/**
	 * 物流单号
	 */
	private String deliverySn;
	/**
	 * 自动确认天数
	 */
	private Integer autoConfirmDay;
	/**
	 * 获得积分
	 */
	private Integer integration;
	/**
	 * 成长值
	 */
	private Integer growth;
	/**
	 * 发票类型(0不开发票 1电子发票 2纸质)
	 */
	private Integer billType;
	/**
	 * 发票抬头
	 */
	private String billHeader;
	/**
	 * 发票内容
	 */
	private String billContent;
	/**
	 * 收票电话
	 */
	private String billReceiverPhone;
	/**
	 * 收票邮箱
	 */
	private String billReceiverEmail;
	/**
	 * 收货人
	 */
	private String receiverName;
	/**
	 * 收货电话
	 */
	private String receiverPhone;
	/**
	 * 邮编
	 */
	private String receiverPostCode;
	/**
	 * 省份
	 */
	private String receiverProvince;
	/**
	 * 城市
	 */
	private String receiverCity;
	/**
	 * 区县
	 */
	private String receiverRegion;
	/**
	 * 详细地址
	 */
	private String receiverDetailAddress;
	/**
	 * 订单备注
	 */
	private String note;
	/**
	 * 确认状态
	 */
	private Integer confirmStatus;
	/**
	 * 删除状态
	 */
	private Integer deleteStatus;
	/**
	 * 使用积分
	 */
	private Integer useIntegration;
	/**
	 * 支付时间
	 */
	private Date paymentTime;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 签收时间
	 */
	private Date receiveTime;
	/**
	 * 评价时间
	 */
	private Date commentTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;

}
