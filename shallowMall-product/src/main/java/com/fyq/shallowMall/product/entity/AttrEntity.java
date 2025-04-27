package com.fyq.shallowMall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品属性表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性ID
	 */
	@TableId
	private Long attrId;
	/**
	 * 属性名称[7](@ref)
	 */
	private String attrName;
	/**
	 * 检索类型[0=不需要 1=需要][7](@ref)
	 */
	private Integer searchType;
	/**
	 * 图标路径
	 */
	private String icon;
	/**
	 * 可选值列表(逗号分隔)[7](@ref)
	 */
	private String valueSelect;
	/**
	 * 属性类型[0=销售属性 1=基本属性 2=双重属性][3](@ref)
	 */
	private Integer attrType;
	/**
	 * 启用状态[0=禁用 1=启用][7](@ref)
	 */
	private Integer enable;
	/**
	 * 分类ID[3](@ref)
	 */
	private Long catalogId;
	/**
	 * 快速展示[0=不展示 1=展示]
	 */
	private Integer showDesc;

}
