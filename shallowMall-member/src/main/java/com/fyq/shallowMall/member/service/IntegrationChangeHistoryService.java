package com.fyq.shallowMall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变更记录
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

