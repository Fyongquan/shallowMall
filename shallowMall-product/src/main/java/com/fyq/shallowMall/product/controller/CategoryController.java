package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.CategoryService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 商品分类表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entities = categoryService.listWithTree();

        return R.ok().put("data", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 级联修改
     */
    @Transactional
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
//		categoryService.updateById(category);

        categoryService.updateCategory(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){

         categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

    /**
     * 保存排序
     */
    @PostMapping("/update/sort")
    public R updateSort(@RequestBody List<CategoryEntity> categoryEntities){
        categoryService.updateBatchById(categoryEntities);
        return R.ok();
    }
}
