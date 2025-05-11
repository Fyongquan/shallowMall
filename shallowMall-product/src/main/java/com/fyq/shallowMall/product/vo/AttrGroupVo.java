package com.fyq.shallowMall.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fyq.shallowMall.product.entity.AttrGroupEntity;
import lombok.Data;

@Data
public class AttrGroupVo extends AttrGroupEntity {

    /**
     * 分类ID完整路径
     */
    private Long[] catalogPath;
}
