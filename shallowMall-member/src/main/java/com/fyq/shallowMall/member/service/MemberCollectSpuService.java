package com.fyq.shallowMall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 商品收藏表
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

