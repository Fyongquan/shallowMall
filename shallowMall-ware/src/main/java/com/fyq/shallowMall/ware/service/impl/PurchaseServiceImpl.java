package com.fyq.shallowMall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.constant.WareConstant;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.ware.dao.PurchaseDao;
import com.fyq.shallowMall.ware.entity.PurchaseDetailEntity;
import com.fyq.shallowMall.ware.entity.PurchaseEntity;
import com.fyq.shallowMall.ware.entity.WareInfoEntity;
import com.fyq.shallowMall.ware.entity.WareSkuEntity;
import com.fyq.shallowMall.ware.service.PurchaseDetailService;
import com.fyq.shallowMall.ware.service.PurchaseService;
import com.fyq.shallowMall.ware.service.WareSkuService;
import com.fyq.shallowMall.ware.vo.MergeVo;
import com.fyq.shallowMall.ware.vo.PurchaseDoneVo;
import com.fyq.shallowMall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                //只查询新建和已分配的采购单
                new QueryWrapper<PurchaseEntity>().eq("status", WareConstant.PurchaseEnum.CREATED.getCode()).or().eq("status", WareConstant.PurchaseEnum.ASSIGNED.getCode())
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        //如果没有传入采购单id，则新建一个
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();

            purchaseEntity.setStatus(WareConstant.PurchaseEnum.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        } else {
            //只有新建和已分配状态的采购单才能合并
            PurchaseEntity purchaseEntity = this.getById(purchaseId);
            if (purchaseEntity.getStatus() != WareConstant.PurchaseEnum.CREATED.getCode() && purchaseEntity.getStatus() != WareConstant.PurchaseEnum.ASSIGNED.getCode()) {
                return;
            }
        }

        //只有新建和已分配状态的采购需求才能合并
        LambdaQueryWrapper<PurchaseDetailEntity> purchaseDetailEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        purchaseDetailEntityLambdaQueryWrapper.in(PurchaseDetailEntity::getId, mergeVo.getItems());
        List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.list(purchaseDetailEntityLambdaQueryWrapper);
        purchaseDetailEntities = purchaseDetailEntities.stream()
                .filter(item -> item.getStatus() == WareConstant.PurchaseDetailEnum.CREATED.getCode() || item.getStatus() == WareConstant.PurchaseDetailEnum.ASSIGNED.getCode())
                .collect(Collectors.toList());
        if (purchaseDetailEntities.isEmpty()) {
            return;
        }

        final Long finalPurchaseId = purchaseId;
        //合并采购需求
        purchaseDetailEntities = mergeVo.getItems().stream().map(item -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setId(item);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailEnum.ASSIGNED.getCode());
            return detailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(purchaseDetailEntities);
    }

    @Transactional
    @Override
    public void received(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return; // 避免空指针异常
        }
        List<Long> assignedIds = new ArrayList<>();
        //1、确认当前采购单是已分配状态
        List<PurchaseEntity> purchaseEntities = ids.stream()
                .map(this::getById)
                .filter(Objects::nonNull) // 过滤掉 getById 返回为 null 的情况
                .filter(purchaseEntity -> {
                    if (purchaseEntity.getStatus() == WareConstant.PurchaseEnum.ASSIGNED.getCode()) {
                        assignedIds.add(purchaseEntity.getId());
                        return true;
                    } else {
                        return false;
                    }
                })
                .peek(purchaseEntity -> purchaseEntity.setStatus(WareConstant.PurchaseEnum.RECEIVED.getCode()))
                .collect(Collectors.toList());

        //2、修改采购单为已领取状态
        if (!purchaseEntities.isEmpty()) {
            this.updateBatchById(purchaseEntities);
        }

        //3、修改采购项为正在采购状态
        if (!assignedIds.isEmpty()) {
            LambdaUpdateWrapper<PurchaseDetailEntity> purchaseDetailEntityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            purchaseDetailEntityLambdaUpdateWrapper.in(PurchaseDetailEntity::getPurchaseId, assignedIds);
            purchaseDetailEntityLambdaUpdateWrapper.set(PurchaseDetailEntity::getStatus, WareConstant.PurchaseDetailEnum.BUYING.getCode());
            purchaseDetailService.update(purchaseDetailEntityLambdaUpdateWrapper);
        }
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        //1、如果采购项全部完成，则采购单状态改为已完成，否则采购单状态改为有异常
        Long id = purchaseDoneVo.getId();

        List<PurchaseItemDoneVo> purchaseDetailEntityList = purchaseDoneVo.getItems();
        if (purchaseDetailEntityList == null || purchaseDetailEntityList.isEmpty()) {
            return;
        }

        //标记采购项是否全部完成
        boolean allFinished = true;

        List<PurchaseDetailEntity> purchaseDetailEntities = new ArrayList<>();
        for(PurchaseItemDoneVo item : purchaseDetailEntityList){
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            if(item.getStatus() == WareConstant.PurchaseDetailEnum.FINISHED.getCode()){
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.FINISHED.getCode());
                //将成功采购的采购项进行入库
                PurchaseDetailEntity detailEntity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock( detailEntity.getSkuId(), detailEntity.getWareId(), detailEntity.getSkuNum());
            } else if(item.getStatus() == WareConstant.PurchaseDetailEnum.HASERRORED.getCode()){
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.HASERRORED.getCode());
                allFinished = false;
            }
            purchaseDetailEntity.setId(item.getItemId());
            purchaseDetailEntities.add(purchaseDetailEntity);
        }
        purchaseDetailService.updateBatchById(purchaseDetailEntities);

        //更新采购单状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(allFinished ? WareConstant.PurchaseEnum.FINISHED.getCode() : WareConstant.PurchaseEnum.HASERRORED.getCode());
        this.updateById(purchaseEntity);

    }
}
