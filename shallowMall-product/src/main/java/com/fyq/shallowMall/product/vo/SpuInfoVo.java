package com.fyq.shallowMall.product.vo;

import com.fyq.shallowMall.product.entity.SpuInfoEntity;
import lombok.Data;

@Data
public class SpuInfoVo extends SpuInfoEntity {
    String brandName;
    String catalogName;
}
