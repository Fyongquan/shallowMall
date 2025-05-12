package com.fyq.shallowMall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import com.fyq.common.to.SpuBoundsTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.coupon.entity.SpuBoundsEntity;
import com.fyq.shallowMall.coupon.service.SpuBoundsService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 商品spu积分设置
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 17:50:59
 */
@RestController
@RequestMapping("coupon/spubounds")
@Slf4j
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuBoundsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuBoundsEntity spuBounds = spuBoundsService.getById(id);

        return R.ok().put("spuBounds", spuBounds);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuBoundsEntity spuBoundsEntity){
        log.info("保存商品积分信息,{}", spuBoundsEntity);
		spuBoundsService.save(spuBoundsEntity);

        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/saveSpuBounds")
    public R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo){
        spuBoundsService.saveSpuBoundsTo(spuBoundsTo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.updateById(spuBounds);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
