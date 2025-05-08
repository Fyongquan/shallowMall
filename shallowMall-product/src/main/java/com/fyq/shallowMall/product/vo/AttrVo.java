package com.fyq.shallowMall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class AttrVo {
    private static final long serialVersionUID = 1L;

    /**
     * 属性ID
     */
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
    /**
     * 可选值类型
     */
    private Integer valueType;

    private Long attrGroupId;
}
