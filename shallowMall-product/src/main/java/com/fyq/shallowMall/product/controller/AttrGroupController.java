package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fyq.shallowMall.product.annotation.LogAnnotation;
import com.fyq.shallowMall.product.entity.AttrAttrgroupRelationEntity;
import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.service.CategoryService;
import com.fyq.shallowMall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import com.fyq.shallowMall.product.service.AttrGroupService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 属性分组表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list/{catalogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catalogId") Long catalogId){
//        PageUtils page = attrGroupService.queryPage(params);

        PageUtils page = attrGroupService.queryPage(params, catalogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        attrGroup.setCatalogPath(categoryService.getCatalogPath(attrGroup.getCatalogId()));

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * 批量删除关联关系
     * @param relationEntities
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelationBatch(@RequestBody List<AttrGroupRelationVo> relationEntities){
        attrGroupService.removeRelationBatch(relationEntities);

        return R.ok();
    }

    /**
     * 查询属性分组关联属性
     * @param attrGroupId
     * @return
     */
    @LogAnnotation
    @GetMapping("/{attrGroupId}/attr/relation")
    public R getAttrRelation(@PathVariable("attrGroupId") Long attrGroupId){

        List<AttrEntity> attrEntityList = attrGroupService.getAttrRelation(attrGroupId);

        return R.ok().put("data", attrEntityList);
    }

    /**
     * 查询属性分组没有关联的属性
     * @param params
     * @return
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R queryNoattrPage(@RequestParam Map<String, Object> params, @PathVariable Long attrGroupId){

        PageUtils page = attrGroupService.queryNoattrPage(params,attrGroupId);

        return R.ok().put("page", page);
    }

    /**
     * 添加属性关联
     * @param relationEntities
     */
    @PostMapping("/attr/relation")
    public void addRelation(@RequestBody List<AttrGroupRelationVo> relationEntities){
        attrGroupService.saveBatch(relationEntities);
    }

}
