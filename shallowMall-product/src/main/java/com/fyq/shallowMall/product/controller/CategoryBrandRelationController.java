package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.entity.CategoryBrandRelationEntity;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌分类关联
 *
 * @author dengwei
 * @email dengwei@gmail.com
 * @date 2024-04-06 03:17:30
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    //http://localhost:88/api/product/categorybrandrelation/brands/list?t=1722296513255&catId=225
    /**
     * 获取分类关联的品牌
     */
    @GetMapping("/brands/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R queryCategoryRelatedBrands(@RequestParam("catId") Long catId){
        List<BrandEntity> result = this.categoryBrandRelationService.queryCategoryRelatedBrands(catId);

        return R.ok().put("data", result);
    }
    /**
     * 根据品牌brandId查询关联的分类列表
     */
    @GetMapping("/catalog/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R queryCatalogList(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> result = this.categoryBrandRelationService.queryCatalogList(brandId);

        return R.ok().put("data", result);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存品牌关联分类
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        this.categoryBrandRelationService.saveCategorybrandrelation(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}