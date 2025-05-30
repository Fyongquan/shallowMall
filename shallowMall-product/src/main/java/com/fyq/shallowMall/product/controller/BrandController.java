package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fyq.common.valid.AddGroup;
import com.fyq.common.valid.UpdateGroup;
import com.fyq.common.valid.UpdateStatusGroup;
import com.fyq.shallowMall.product.annotation.LogAnnotation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.service.BrandService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @LogAnnotation
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */

    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = AddGroup.class) @RequestBody BrandEntity brand/*, BindingResult result*/){
//        if(result.hasErrors()){
//            Map<String, String> errors = new HashMap<>();
//            result.getFieldErrors().forEach(error -> {
//                String field = error.getField();
//                String defaultMessage = error.getDefaultMessage();
//                errors.put(field, defaultMessage);
//            });
//            return R.error(400, "提交的数据不合法").put("data", errors);
//        }
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 级联修改
     */

    @LogAnnotation
    @RequestMapping("/update")
    public R update(@Validated(value = UpdateGroup.class) @RequestBody BrandEntity brand){
//		brandService.updateById(brand);

        brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @PostMapping("update/status")
    public R updateStatus(@Validated(value = UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
