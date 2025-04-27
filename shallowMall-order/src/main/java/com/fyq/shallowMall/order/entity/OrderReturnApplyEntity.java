package com.fyq.shallowMall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 退货申请表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:03:57
 */
@Data
@TableName("oms_order_return_apply")
public class OrderReturnApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 申请ID
	 */
	@TableId
	private Long id;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * SKU ID
	 */
	private Long skuId;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 申请时间
	 */
	private Date createTime;
	/**
	 * 会员账号
	 */
	private String memberUsername;
	/**
	 * 退款金额
	 */
	private BigDecimal returnAmount;
	/**
	 * 退货人
	 */
	private String returnName;
	/**
	 * 退货电话
	 */
	private String returnPhone;
	/**
	 * 处理状态
	 */
	private Integer status;
	/**
	 * 处理时间
	 */
	private Date handleTime;
	/**
	 * 商品图片
	 */
	private String skuImg;
	/**
	 * 商品名称
	 */
	private String skuName;
	/**
	 * 商品品牌
	 */
	private String skuBrand;
	/**
	 * 销售属性(JSON)
	 */
	private String skuAttrsVals;
	/**
	 * 退货数量
	 */
	private Integer skuCount;
	/**
	 * 商品单价
	 */
	private BigDecimal skuPrice;
	/**
	 * 实付单价
	 */
	private BigDecimal skuRealPrice;
	/**
	 * 退货原因
	 */
	private String reason;
	/**
	 * 问题描述
	 */
	private String description;
	/**
	 * 凭证图片(逗号分隔)
	 */
	private String descPics;
	/**
	 * 处理备注
	 */
	private String handleNote;
	/**
	 * 处理人
	 */
	private String handleMan;
	/**
	 * 收货人
	 */
	private String receiveMan;
	/**
	 * 收货时间
	 */
	private Date receiveTime;
	/**
	 * 收货备注
	 */
	private String receiveNote;
	/**
	 * 收货电话
	 */
	private String receivePhone;
	/**
	 * 公司地址
	 */
	private String companyAddress;

}
