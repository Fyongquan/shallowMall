package com.fyq.shallowMall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.ware.entity.PurchaseEntity;
import com.fyq.shallowMall.ware.vo.MergeVo;
import com.fyq.shallowMall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:06:23
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo purchaseDoneVo);
}

