package com.fyq.shallowMall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.common.utils.R;
import com.fyq.shallowMall.ware.dao.WareSkuDao;
import com.fyq.shallowMall.ware.entity.WareSkuEntity;
import com.fyq.shallowMall.ware.feign.ProductFeignService;
import com.fyq.shallowMall.ware.service.WareSkuService;
import com.fyq.common.to.SkuHasStockTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryWareSkuPage(Map<String, Object> params, Long skuId, Long wareId) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        if (skuId != null) {
            wrapper.eq(WareSkuEntity::getSkuId, skuId);
        }
        if (wareId != null) {
            wrapper.eq(WareSkuEntity::getWareId, wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //如果有这个库存记录，就修改，没有就新增
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId);
        WareSkuEntity wareSkuEntity = this.getOne(wrapper);
        if (wareSkuEntity != null) {
            wareSkuEntity.setStock(wareSkuEntity.getStock() + skuNum);
            this.saveOrUpdate(wareSkuEntity);
        } else {
            WareSkuEntity skuEntity = new WareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setWareId(wareId);
            skuEntity.setStock(skuNum);
            skuEntity.setStockLocked(0);
            //TODO 异常出现但是不回滚
            try{
                R info = productFeignService.getSkuInfo(skuId);
                Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                if (info.getCode() == 0) {
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            this.save(skuEntity);
        }
    }

    @Override
    public List<SkuHasStockTo> hasStock(List<Long> skuIds) {
        List<SkuHasStockTo> skuHasStockVos = new ArrayList<>();
        for (Long skuId : skuIds) {
            WareSkuEntity skuEntity = this.getOne(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId));
            SkuHasStockTo skuHasStockVo = new SkuHasStockTo();
            skuHasStockVo.setSkuId(skuId);
            //如果没有库存信息直接返回没有库存
            if(skuEntity == null){
                skuHasStockVo.setHasStock(false);
            }else{
                Integer stock = skuEntity.getStock();
                Integer stockLocked = skuEntity.getStockLocked();

                // 默认值处理：null 视为 0
                int availableStock = (stock != null ? stock : 0) - (stockLocked != null ? stockLocked : 0);

                if (availableStock <= 0) {
                    skuHasStockVo.setHasStock(false);
                } else {
                    skuHasStockVo.setHasStock(true);
                }
            }
            skuHasStockVos.add(skuHasStockVo);
        }
        return skuHasStockVos;
    }


}