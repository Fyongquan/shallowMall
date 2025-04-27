package com.fyq.shallowMall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单明细表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:03:57
 */
@Data
@TableName("oms_order_item")
public class OrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 明细ID
	 */
	@TableId
	private Long id;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * SPU ID
	 */
	private Long spuId;
	/**
	 * 商品名称
	 */
	private String spuName;
	/**
	 * 商品图片
	 */
	private String spuPic;
	/**
	 * 品牌名称
	 */
	private String spuBrand;
	/**
	 * 分类ID
	 */
	private Long categoryId;
	/**
	 * SKU ID
	 */
	private Long skuId;
	/**
	 * SKU名称
	 */
	private String skuName;
	/**
	 * SKU图片
	 */
	private String skuPic;
	/**
	 * SKU单价
	 */
	private BigDecimal skuPrice;
	/**
	 * 购买数量
	 */
	private Integer skuQuantity;
	/**
	 * 销售属性(JSON格式)
	 */
	private String skuAttrsVals;
	/**
	 * 促销分摊
	 */
	private BigDecimal promotionAmount;
	/**
	 * 优惠券分摊
	 */
	private BigDecimal couponAmount;
	/**
	 * 积分分摊
	 */
	private BigDecimal integrationAmount;
	/**
	 * 实付金额
	 */
	private BigDecimal realAmount;
	/**
	 * 赠送积分
	 */
	private Integer giftIntegration;
	/**
	 * 赠送成长值
	 */
	private Integer giftGrowth;

}
