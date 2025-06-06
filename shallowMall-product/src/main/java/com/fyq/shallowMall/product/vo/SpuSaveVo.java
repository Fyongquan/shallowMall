/**
  * Copyright 2025 bejson.com 
  */
package com.fyq.shallowMall.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2025-05-11 21:54:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private Integer publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;
}