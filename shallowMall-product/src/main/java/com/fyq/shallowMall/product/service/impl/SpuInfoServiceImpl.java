package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.to.MemberPriceTo;
import com.fyq.common.to.SkuFullReductionTo;
import com.fyq.common.to.SkuLadderTo;
import com.fyq.common.to.SpuBoundsTo;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.common.utils.R;
import com.fyq.shallowMall.product.dao.SpuInfoDao;
import com.fyq.shallowMall.product.entity.*;
import com.fyq.shallowMall.product.feign.CouponFeignService;
import com.fyq.shallowMall.product.service.*;
import com.fyq.shallowMall.product.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpu(SpuSaveVo spuSaveVo) {
        //1、保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        this.save(spuInfoEntity);

        //2、保存spu的描述图片 pms_spu_info_desc
        //spuId在save之后自动回填
        spuInfoDescService.saveSpuInfoDesc(spuInfoEntity.getId(), spuSaveVo.getDecript());

        //3、保存spu的图片集 pms_spu_images
        spuImagesService.saveImages(spuInfoEntity.getId(), spuSaveVo.getImages());

        //4、保存spu的规格参数 pms_product_attr_value
        productAttrValueService.saveProductAttr(spuInfoEntity.getId(), spuSaveVo.getBaseAttrs());

        //5、保存spu的积分信息 sms_spu_bounds（远程调用）
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        spuBoundsTo.setSpuId(spuInfoEntity.getId());
        BeanUtils.copyProperties(spuSaveVo.getBounds(), spuBoundsTo);
        R r = couponFeignService.saveSpuBounds(spuBoundsTo);
        if (r.getCode() != 0) {
            log.error("远程调用spu积分信息保存方法失败");
        }

        //6、保存当前spu对应的所有sku信息；
        //6.1）、保存sku基本信息 pms_sku_info
        List<Skus> skus = spuSaveVo.getSkus();
        skus.forEach(sku -> {
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(sku, skuInfoEntity);

            // 设置默认图片
            for (Images img : sku.getImages()) {
                if (img.getDefaultImg() == 1) {
                    skuInfoEntity.setSkuDefaultImg(img.getImgUrl());
                    break;
                }
            }

            // 设置其他字段
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoService.save(skuInfoEntity);
            Long skuId = skuInfoEntity.getSkuId();

            //6.2）、保存sku的图片信息 pms_sku_images
            List<SkuImagesEntity> skuImagesEntities = new ArrayList<>();
            for (Images img : sku.getImages()) {
                if (StringUtils.isEmpty(img.getImgUrl())) {
                    continue;
                }
                SkuImagesEntity entity = new SkuImagesEntity();
                BeanUtils.copyProperties(img, entity);
                entity.setSkuId(skuId);
                skuImagesEntities.add(entity);
            }
            skuImagesService.saveBatch(skuImagesEntities);

            //6.3）、保存sku的销售属性 pms_sku_sale_attr_value
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = new ArrayList<>();
            for (Attr attr : sku.getAttr()) {
                SkuSaleAttrValueEntity entity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(attr, entity);
                entity.setSkuId(skuId);
                skuSaleAttrValueEntities.add(entity);
            }
            skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            //6.4）、保存sku的优惠、满减等信息 sms_sku_ladder\sms_sku_full_reduction\sms_member_price
            SkuLadderTo skuLadderTo = new SkuLadderTo();
            BeanUtils.copyProperties(sku, skuLadderTo);
            skuLadderTo.setSkuId(skuId);
            skuLadderTo.setAddOther(sku.getCountStatus());
            skuLadderTo.setPrice(sku.getPrice().multiply(skuLadderTo.getDiscount()));
            if (skuLadderTo.getFullCount() > 0) {
                R r1 = couponFeignService.saveSkuLadder(skuLadderTo);
                if (r1.getCode() != 0) {
                    log.error("远程调用sku优惠信息保存方法失败");
                }
            }

            SkuFullReductionTo skuReductionTo = new SkuFullReductionTo();
            BeanUtils.copyProperties(sku, skuReductionTo);
            skuReductionTo.setSkuId(skuId);
            skuReductionTo.setAddOther(sku.getCountStatus());
            if (skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) == 1) {
                R r2 = couponFeignService.saveSkuFullReduction(skuReductionTo);
                if (r2.getCode() != 0) {
                    log.error("远程调用sku满减信息保存方法失败");
                }
            }

            List<MemberPriceTo> memberPriceTos = sku.getMemberPrice().stream().map(mp -> {
                MemberPriceTo memberPrice = new MemberPriceTo();
                memberPrice.setMemberLevelId(mp.getId());
                memberPrice.setMemberLevelName(mp.getName());
                memberPrice.setMemberPrice(mp.getPrice());
                memberPrice.setSkuId(skuId);
                memberPrice.setAddOther(sku.getCountStatus());
                return memberPrice;
            }).filter(mp -> mp.getMemberPrice().compareTo(BigDecimal.ZERO) == 1).collect(Collectors.toList());
            R r3 = couponFeignService.saveMemberPriceBatch(memberPriceTos);
            if (r3.getCode() != 0) {
                log.error("远程调用sku会员信息保存方法失败");
            }
        });
    }

    @Override
    public PageUtils querySpuInfoPage(Map<String, Object> params, Long catalogId, Integer status, String key, Long brandId) {
        LambdaQueryWrapper<SpuInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (catalogId != null) {
            wrapper.eq(SpuInfoEntity::getCatalogId, catalogId);
        }
        if (brandId != null) {
            wrapper.eq(SpuInfoEntity::getBrandId, brandId);
        }
        if (status != null) {
            wrapper.eq(SpuInfoEntity::getPublishStatus, status);
        }
        if (StringUtils.isNotEmpty(key)) {
            wrapper.and(w1 -> {
                w1.eq(SpuInfoEntity::getId, key);
                w1.or().like(SpuInfoEntity::getSpuName, key);
            });
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        List<SpuInfoEntity> records = page.getRecords();
        List<SpuInfoVo> spuInfoVos = new ArrayList<>();
        records.forEach(spuInfoEntity -> {
            SpuInfoVo spuInfoVo = new SpuInfoVo();
            BeanUtils.copyProperties(spuInfoEntity, spuInfoVo);
            BrandEntity brandEntity = brandService.getById(spuInfoEntity.getBrandId());
            if (brandEntity != null) {
                spuInfoVo.setBrandName(brandEntity.getName());
            }
            CategoryEntity categoryEntity = categoryService.getById(spuInfoEntity.getCatalogId());
            if (categoryEntity != null) {
                spuInfoVo.setCatalogName(categoryEntity.getName());
            }
            spuInfoVos.add(spuInfoVo);
        });
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(spuInfoVos);
        return pageUtils;
    }

}