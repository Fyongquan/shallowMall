package com.fyq.shallowMall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fyq.shallowMall.ware.vo.MergeVo;
import com.fyq.shallowMall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fyq.shallowMall.ware.entity.PurchaseEntity;
import com.fyq.shallowMall.ware.service.PurchaseService;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.R;



/**
 * 采购信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:06:23
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询未领取的采购单
     * @param params
     * @return
     */
    @GetMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageUnreceivePurchase(params);

        return R.ok().put("page", page);
    }

    /**
     * 合并采购需求
     * @param mergeVo
     * @return
     */
    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo){
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    /**
     * 领取采购单
     * @param ids
     * @return
     */
    @PostMapping("/received")
    public R received(@RequestBody List<Long> ids){
        purchaseService.received(ids);
        return R.ok();
    }

    /**
     * 完成采购单
     * @return
     */
    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo purchaseDoneVo){
        purchaseService.done(purchaseDoneVo);
        return R.ok();
    }

}
