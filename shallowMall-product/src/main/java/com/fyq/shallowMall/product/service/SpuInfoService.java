package com.fyq.shallowMall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.product.entity.SpuInfoEntity;
import com.fyq.shallowMall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpu(SpuSaveVo spuSaveVo);

    PageUtils querySpuInfoPage(Map<String, Object> params, Long catalogId, Integer status, String key, Long brandId);
}

