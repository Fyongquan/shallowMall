package com.fyq.shallowMall.ware.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.ware.dao.PurchaseDetailDao;
import com.fyq.shallowMall.ware.entity.PurchaseDetailEntity;
import com.fyq.shallowMall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPurchaseDetailPage(Map<String, Object> params, Long wareId, Integer status, String key) {
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper = new LambdaQueryWrapper<>();
        if (wareId != null) {
            wrapper.eq(PurchaseDetailEntity::getWareId, wareId);
        }
        if (status != null) {
            wrapper.eq(PurchaseDetailEntity::getStatus, status);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w1 -> {
                w1.eq(PurchaseDetailEntity::getSkuId, key);
                w1.or().eq(PurchaseDetailEntity::getPurchaseId, key);
            });
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}