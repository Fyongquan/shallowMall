package com.fyq.shallowMall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyq.common.utils.PageUtils;
import com.fyq.shallowMall.member.entity.MemberReceiveAddressEntity;

import java.util.Map;

/**
 * 收货地址
 *
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 18:00:34
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

