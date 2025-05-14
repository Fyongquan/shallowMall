package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fyq.shallowMall.product.Aspect.LogAspect;
import com.fyq.shallowMall.product.annotation.LogAnnotation;
import com.fyq.shallowMall.product.entity.ProductAttrValueEntity;
import com.fyq.shallowMall.product.service.ProductAttrValueService;
import com.fyq.shallowMall.product.vo.AttrRespVo;
import com.fyq.shallowMall.product.vo.AttrVo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.service.AttrService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 商品属性表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 16:40:05
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 单独回显
     */
    @LogAnnotation
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrRespVo attrRespVo = attrService.getAttrRespVo(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */

    @PostMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */

    @PostMapping("/update")
    public R update(@RequestBody AttrVo attrVo){
		attrService.updateAttrCascade(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

    /**
     * 根据分类id查询出所有的属性，根据属性类型获取对应的属性
     * @param params
     * @param categoryId
     * @param attrType
     * @return
     */
    @LogAnnotation
    @GetMapping("/{attrType}/list/{catalogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catalogId") Long categoryId,
                  @PathVariable("attrType") String attrType){
        PageUtils page = attrService.queryBaseAttrPage(params, categoryId, attrType);

        return R.ok().put("page", page);
    }

    /**
     * 根据spuId查询出所有的属性关联
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R listforspu(@PathVariable("spuId") Long spuId){
        List<ProductAttrValueEntity> entities = productAttrValueService.listBySpuId(spuId);
        return R.ok().put("data", entities);
    }

    /**
     * 批量修改属性关联
     * @param entities
     * @return
     */
    @PostMapping("/update/{spuId}")
    public R updateAttrValues(@RequestBody List<ProductAttrValueEntity> entities,
                              @PathVariable("spuId") Long spuId){
        productAttrValueService.updateAttrValueBatch(entities, spuId);
        return R.ok();
    }
}
