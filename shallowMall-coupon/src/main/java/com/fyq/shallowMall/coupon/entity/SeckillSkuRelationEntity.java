package com.fyq.shallowMall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 秒杀商品配置
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 关联ID
	 */
	@TableId
	private Long id;
	/**
	 * 活动ID
	 */
	private Long promotionId;
	/**
	 * 时段ID
	 */
	private Long promotionSessionId;
	/**
	 * 商品SKU
	 */
	private Long skuId;
	/**
	 * 秒杀价
	 */
	private BigDecimal seckillPrice;
	/**
	 * 总库存
	 */
	private Integer seckillCount;
	/**
	 * 限购数
	 */
	private Integer seckillLimit;
	/**
	 * 展示排序
	 */
	private Integer seckillSort;

}
