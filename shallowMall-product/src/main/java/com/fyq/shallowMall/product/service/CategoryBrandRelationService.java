package com.fyq.shallowMall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author dengwei
 * @email dengwei@gmail.com
 * @date 2024-04-06 02:22:49
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BrandEntity> queryCategoryRelatedBrands(Long catId);

    List<CategoryBrandRelationEntity> queryCatalogList(Long brandId);

    void saveCategorybrandrelation(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(String newBrandName, Long brandId);

    void updateCategory(String newCategoryName, Long catId);
}