package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.dao.BrandDao;
import com.fyq.shallowMall.product.dao.CategoryBrandRelationDao;
import com.fyq.shallowMall.product.dao.CategoryDao;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;
import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BrandEntity> queryCategoryRelatedBrands(Long catId) {
        return null;
    }

    @Override
    public List<CategoryBrandRelationEntity> queryCatalogList(Long brandId) {

        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        return this.list(wrapper);
    }

    @Override
    public void saveCategorybrandrelation(CategoryBrandRelationEntity categoryBrandRelation) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryEntity::getCatId, categoryBrandRelation.getCatalogId());
        CategoryEntity categoryEntity = categoryDao.selectOne(wrapper);
        categoryBrandRelation.setCatalogName(categoryEntity.getName());

        LambdaQueryWrapper<BrandEntity> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(BrandEntity::getBrandId, categoryBrandRelation.getBrandId());
        BrandEntity brandEntity = brandDao.selectOne(wrapper1);
        categoryBrandRelation.setBrandName(brandEntity.getName());

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(String newBrandName, Long brandId) {
        CategoryBrandRelationEntity categoryBrandRelation = new CategoryBrandRelationEntity();
        categoryBrandRelation.setBrandName(newBrandName);
        categoryBrandRelation.setBrandId(brandId);

        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, categoryBrandRelation.getBrandId());

        this.update(categoryBrandRelation, wrapper);
    }

    @Override
    public void updateCategory(String newCategoryName, Long catId) {
        CategoryBrandRelationEntity categoryBrandRelation = new CategoryBrandRelationEntity();
        categoryBrandRelation.setCatalogName(newCategoryName);
        categoryBrandRelation.setCatalogId(catId);

        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getCatalogId, categoryBrandRelation.getCatalogId());

        this.update(categoryBrandRelation, wrapper);
    }
}