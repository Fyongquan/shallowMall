/**
  * Copyright 2025 bejson.com 
  */
package com.fyq.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Auto-generated: 2025-05-11 21:54:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class MemberPriceTo {
    private Long skuId;
    private Long memberLevelId;
    private String memberLevelName;
    private BigDecimal memberPrice;
    private Integer addOther;
}