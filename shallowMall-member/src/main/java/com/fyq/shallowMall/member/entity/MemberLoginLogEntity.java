package com.fyq.shallowMall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 登录日志
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
@Data
@TableName("ums_member_login_log")
public class MemberLoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志ID
	 */
	@TableId
	private Long id;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * IPv4/IPv6地址
	 */
	private String ip;
	/**
	 * 登录城市
	 */
	private String city;
	/**
	 * 方式[1-WEB 2-APP 3-小程序]
	 */
	private Integer loginType;

}
