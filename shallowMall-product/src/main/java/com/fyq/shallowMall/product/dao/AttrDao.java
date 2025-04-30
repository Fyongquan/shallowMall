package com.fyq.shallowMall.product.dao;

import com.fyq.shallowMall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性表
 * 
 * @author fuyongquan
 * @email 1304184660@qq.com
 * @date 2025-04-27 15:56:45
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

//    添加value_type字段
//    <result property="valueType" column="value_type"/>
//    还需要去vo下添加value_type字段
    //TODO
}
