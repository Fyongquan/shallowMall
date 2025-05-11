package com.fyq.shallowMall.product.vo;

import com.fyq.shallowMall.product.entity.AttrEntity;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo extends AttrGroupEntity {

    private List<AttrEntity> attrs;
}
