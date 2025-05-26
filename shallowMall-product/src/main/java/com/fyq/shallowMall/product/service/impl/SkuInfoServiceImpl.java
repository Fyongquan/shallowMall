package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.product.dao.SkuInfoDao;
import com.fyq.shallowMall.product.entity.SkuInfoEntity;
import com.fyq.shallowMall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId, String key, Long brandId, BigDecimal min, BigDecimal max) {

        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (catalogId != null && catalogId != 0){
            wrapper.eq(SkuInfoEntity::getCatalogId, catalogId);
        }
        if (brandId != null && brandId != 0){
            wrapper.eq(SkuInfoEntity::getBrandId, brandId);
        }
        if(!StringUtils.isEmpty(key)){
            wrapper.and(w1 -> {
                w1.eq(SkuInfoEntity::getSkuId, key);
                w1.or().like(SkuInfoEntity::getSkuName, key);
            });
        }
        if(min != null && min.compareTo(BigDecimal.ZERO) > 0){
            wrapper.ge(SkuInfoEntity::getPrice, min);
        }
        if(max != null && max.compareTo(BigDecimal.ZERO) > 0){
            wrapper.le(SkuInfoEntity::getPrice, max);
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkuBySpuId(Long spuId) {
        return this.list(new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSpuId, spuId));
    }

}