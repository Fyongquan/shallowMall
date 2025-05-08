package com.fyq.shallowMall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fyq.shallowMall.product.entity.AttrEntity;
import lombok.Data;

@Data
public class AttrRespVo extends AttrEntity {

    private String catalogName;

    private String groupName;

    private Long[] catalogPath;

    private Long attrGroupId;
}
