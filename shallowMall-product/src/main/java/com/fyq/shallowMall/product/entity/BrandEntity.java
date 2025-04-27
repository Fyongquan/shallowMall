package com.fyq.shallowMall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 品牌表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long brandId;
	/**
	 * 品牌名称[7](@ref)
	 */
	private String name;
	/**
	 * LOGO地址[3](@ref)
	 */
	private String logo;
	/**
	 * 品牌描述
	 */
	private String descript;
	/**
	 * 显示状态[0=隐藏 1=显示]
	 */
	private Integer showStatus;
	/**
	 * 首字母
	 */
	private String firstLetter;
	/**
	 * 排序
	 */
	private Integer sort;

}
