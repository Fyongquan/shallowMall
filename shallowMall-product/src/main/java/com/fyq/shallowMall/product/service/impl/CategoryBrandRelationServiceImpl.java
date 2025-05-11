package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.dao.BrandDao;
import com.fyq.shallowMall.product.dao.CategoryBrandRelationDao;
import com.fyq.shallowMall.product.dao.CategoryDao;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;
import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.BrandService;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import com.fyq.shallowMall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandService brandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取当前品牌关联的所有分类列表
     *
     * @param catId
     * @return
     */
    @Override
    public List<BrandVo> getBrandsByCatId(Long catId) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<CategoryBrandRelationEntity>()
                .eq(CategoryBrandRelationEntity::getCatalogId, catId);
        List<CategoryBrandRelationEntity> relationEntities = this.list(queryWrapper);
        if (relationEntities != null && !relationEntities.isEmpty()) {
            List<Long> brandIds = relationEntities.stream()
                    .map(CategoryBrandRelationEntity::getBrandId).collect(Collectors.toList());
            if (!brandIds.isEmpty()) {
                List<BrandEntity> brandEntities = (List<BrandEntity>) brandService.listByIds(brandIds);
                return brandEntities.stream().map(brandEntity -> {
                    BrandVo brandVo = new BrandVo();
                    brandVo.setBrandId(brandEntity.getBrandId());
                    brandVo.setBrandName(brandEntity.getName());
                    return brandVo;
                }).collect(Collectors.toList());
            }
        }

        return null;
    }

    /**
     * 获取分类关联的品牌
     *
     * @param brandId
     * @return
     */
    @Override
    public List<CategoryBrandRelationEntity> queryCatalogList(Long brandId) {

        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId);
        return this.list(wrapper);
    }

    /**
     * 保存分类关联的品牌
     *
     * @param categoryBrandRelation
     */
    @Override
    public void saveCategorybrandrelation(CategoryBrandRelationEntity categoryBrandRelation) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryEntity::getCatId, categoryBrandRelation.getCatalogId());
        CategoryEntity categoryEntity = categoryDao.selectOne(wrapper);
        if (categoryEntity != null) {
            categoryBrandRelation.setCatalogName(categoryEntity.getName());
        }
        LambdaQueryWrapper<BrandEntity> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(BrandEntity::getBrandId, categoryBrandRelation.getBrandId());
        BrandEntity brandEntity = brandDao.selectOne(wrapper1);
        if (brandEntity != null) {
            categoryBrandRelation.setBrandName(brandEntity.getName());
        }

        this.save(categoryBrandRelation);
    }

    /**
     * 修改品牌关联
     *
     * @param newBrandName
     * @param brandId
     */
    @Override
    public void updateBrand(String newBrandName, Long brandId) {
        CategoryBrandRelationEntity categoryBrandRelation = new CategoryBrandRelationEntity();
        categoryBrandRelation.setBrandName(newBrandName);
        categoryBrandRelation.setBrandId(brandId);

        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, categoryBrandRelation.getBrandId());

        this.update(categoryBrandRelation, wrapper);
    }

    /**
     * 更新与指定分类ID关联的品牌关系中的分类名称
     *
     * @param newCategoryName
     * @param catId
     */
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