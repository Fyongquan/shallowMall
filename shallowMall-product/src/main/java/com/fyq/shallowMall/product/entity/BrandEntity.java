package com.fyq.shallowMall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fyq.common.valid.AddGroup;
import com.fyq.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

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
	@NotNull(message = "修改必须指定id", groups = UpdateGroup.class)
	@Null(message = "新增不能指定id", groups = AddGroup.class)
	private Long brandId;
	/**
	 * 品牌名称
	 */
	@NotBlank(message = "品牌名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * LOGO地址
	 */
	@NotBlank(message = "logo不能为空", groups = AddGroup.class)
	@URL(message = "logo必须是一个合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
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
	@NotEmpty(message = "首字母不能为空", groups = AddGroup.class)
	@Pattern(regexp = "^[a-zA-Z]$",message = "首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空", groups = AddGroup.class)
	@Min(value = 0,message = "排序必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
