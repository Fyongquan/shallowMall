package com.fyq.shallowMall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fyq.common.to.SkuHasStockTo;
import com.fyq.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.ware.entity.WareSkuEntity;
import com.fyq.shallowMall.ware.service.WareSkuService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 商品库存
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:06:23
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestParam(value = "skuId", required = false) Long skuId,
                  @RequestParam(value = "wareId", required = false) Long wareId){
        PageUtils page = wareSkuService.queryWareSkuPage(params, skuId, wareId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("/hasStock")
    public Result<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds){
        List<SkuHasStockTo> skuHasStockVos = wareSkuService.hasStock(skuIds);
        return Result.ok(skuHasStockVos);
    }
}
