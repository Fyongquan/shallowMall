package com.fyq.shallowMall.product.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fyq.shallowMall.product.entity.SkuInfoEntity;
import com.fyq.shallowMall.product.service.SkuInfoService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * sku信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestParam(value = "catalogId", required = false) Long catalogId,
                  @RequestParam(value = "key", required = false) String key,
                  @RequestParam(value = "brandId", required = false) Long brandId,
                  @RequestParam(value = "min",  required = false) BigDecimal min,
                  @RequestParam(value = "max", required = false) BigDecimal max){
        PageUtils page = skuInfoService.queryPage(params, catalogId, key, brandId, min, max);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId){
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] skuIds){
		skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

}
