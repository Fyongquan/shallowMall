package com.fyq.shallowMall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品收藏表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
@Data
@TableName("ums_member_collect_spu")
public class MemberCollectSpuEntity implements Serializable {
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
	 * SPU ID
	 */
	private Long spuId;
	/**
	 * 商品名称
	 */
	private String spuName;
	/**
	 * 商品主图
	 */
	private String spuImg;
	/**
	 * 收藏时间
	 */
	private Date createTime;

}
