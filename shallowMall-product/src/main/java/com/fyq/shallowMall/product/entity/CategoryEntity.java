package com.fyq.shallowMall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 商品分类表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long catId;
	/**
	 * 分类名称[7](@ref)
	 */
	private String name;
	/**
	 * 父分类ID[3](@ref)
	 */
	private Long parentCid;
	/**
	 * 层级[0=一级 1=二级 2=三级][5](@ref)
	 */
	private Integer catLevel;
	/**
	 * 显示状态[0=隐藏 1=显示]
	 */
	@TableLogic(value = "1",delval = "0")
	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标地址
	 */
	private String icon;
	/**
	 * 计量单位[7](@ref)
	 */
	private String productUnit;
	/**
	 * 商品数量
	 */
	private Integer productCount;

	//@TableField(exist = false)表示表中并不存在，这是一个自定义的属性
	@TableField(exist = false)
	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	private List<CategoryEntity> children;

}
