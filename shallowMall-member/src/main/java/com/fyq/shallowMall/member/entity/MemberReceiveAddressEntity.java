package com.fyq.shallowMall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 收货地址
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
@Data
@TableName("ums_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 地址ID
	 */
	@TableId
	private Long id;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 收货人
	 */
	private String receiver;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 邮政编码
	 */
	private String postCode;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 区县
	 */
	private String district;
	/**
	 * 详细地址
	 */
	private String detailAddress;
	/**
	 * 行政区划码
	 */
	private String areacode;
	/**
	 * 默认地址[0-否 1-是]
	 */
	private Integer isDefault;

}
