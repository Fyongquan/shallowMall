package com.fyq.shallowMall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 积分变更记录
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
@Data
@TableName("ums_integration_change_history")
public class IntegrationChangeHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录ID
	 */
	@TableId
	private Long id;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 变更时间
	 */
	private Date createTime;
	/**
	 * 变动值
	 */
	private Integer changeCount;
	/**
	 * 业务备注
	 */
	private String note;
	/**
	 * 来源[0-购物 1-管理员 2-活动]
	 */
	private Integer sourceType;

}
