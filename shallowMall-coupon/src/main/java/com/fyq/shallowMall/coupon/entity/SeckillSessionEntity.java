package com.fyq.shallowMall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import lombok.Data;

/**
 * 秒杀时段
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@Data
@TableName("sms_seckill_session")
public class SeckillSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 场次ID
	 */
	@TableId
	private Long id;
	/**
	 * 时段名称
	 */
	private String name;
	/**
	 * 每日开始
	 */
	private Time startTime;
	/**
	 * 每日结束
	 */
	private Time endTime;
	/**
	 * 启用状态
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;

}
