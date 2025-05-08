package com.fyq.shallowMall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author dengwei
 * @email dengwei@gmail.com
 * @date 2024-04-06 02:22:49
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {
    // 可能需要自定义查询方法，但当前问题仅涉及字段映射，由 MyBatis Plus 自动处理
}