package com.fyq.shallowMall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员等级
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
@Data
@TableName("ums_member_level")
public class MemberLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 等级ID
	 */
	@TableId
	private Long id;
	/**
	 * 等级名称
	 */
	private String name;
	/**
	 * 所需成长值
	 */
	private Integer growthPoint;
	/**
	 * 默认等级[0-否 1-是]
	 */
	private Integer defaultStatus;
	/**
	 * 免运费额度
	 */
	private BigDecimal freeFreightPoint;
	/**
	 * 评价奖励成长值
	 */
	private Integer commentGrowth;
	/**
	 * 免邮特权[0-无 1-有]
	 */
	private Integer priviledgeFreeFreight;
	/**
	 * 生日特权
	 */
	private Integer priviledgeBirthday;
	/**
	 * 配置说明
	 */
	private String note;

}
