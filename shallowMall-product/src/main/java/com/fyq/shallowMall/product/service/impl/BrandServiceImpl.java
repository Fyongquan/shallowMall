package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.product.dao.BrandDao;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            wrapper.eq(BrandEntity::getBrandId, key)
                    .or()
                    .like(BrandEntity::getName, key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {

        BrandEntity oldBrand = this.getById(brand);
        String oldBrandName = oldBrand.getName();
        String newBrandName = brand.getName();

        this.updateById(brand);

        if (!StringUtils.isEmpty(oldBrandName) && !oldBrandName.equals(newBrandName)){
            //TODO 更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(newBrandName,brand.getBrandId());
        }

    }

}