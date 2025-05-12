package com.fyq.shallowMall.coupon.service.impl;

import com.fyq.common.to.SpuBoundsTo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;

import com.fyq.shallowMall.coupon.dao.SpuBoundsDao;
import com.fyq.shallowMall.coupon.entity.SpuBoundsEntity;
import com.fyq.shallowMall.coupon.service.SpuBoundsService;


@Service("spuBoundsService")
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsDao, SpuBoundsEntity> implements SpuBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuBoundsEntity> page = this.page(
                new Query<SpuBoundsEntity>().getPage(params),
                new QueryWrapper<SpuBoundsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSpuBoundsTo(SpuBoundsTo spuBoundsTo) {
        SpuBoundsEntity  spuBoundsEntity = new SpuBoundsEntity();
        BeanUtils.copyProperties(spuBoundsTo,spuBoundsEntity);
        this.save(spuBoundsEntity);
    }

}