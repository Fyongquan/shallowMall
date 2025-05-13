package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.fyq.shallowMall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fyq.shallowMall.product.entity.SpuInfoEntity;
import com.fyq.shallowMall.product.service.SpuInfoService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * spu信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestParam(value = "catalogId", required = false) Long catalogId,
                  @RequestParam(value = "status", required = false) Integer status,
                  @RequestParam(value = "key", required = false) String key,
                  @RequestParam(value = "brandId", required = false) Long brandId){
        PageUtils page = spuInfoService.querySpuInfoPage(params, catalogId, status, key, brandId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuSaveVo spuSaveVo){
		spuInfoService.saveSpu(spuSaveVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
