package com.fyq.shallowMall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.fyq.shallowMall.product.vo.AttrVo;
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

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("data", attr);
    }

    /**
     * 保存
     */
    @Transactional
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/base/list/{catalogId}")
    public R delete(@RequestParam Map<String, Object> params, @PathVariable Long catalogId){
        PageUtils page = attrService.queryPage(params, catalogId);

        return R.ok().put("page", page);
    }

}
