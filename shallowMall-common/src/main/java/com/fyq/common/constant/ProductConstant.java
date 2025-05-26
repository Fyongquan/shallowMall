package com.fyq.common.constant;

import lombok.Getter;

public class ProductConstant {

    /**
     * 属性类型
     */
    @Getter
    public enum AttrEnum{
        ATTR_TYPE_BASE(1, "基本属性"),ATTR_TYPE_SALE(0,"销售属性");

        private final int code;
        private final String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * Spu上架状态
     */
    @Getter
    public enum StatusEnum{
        NEW_SPU(0, "新建"),UP_SPU(1,"商品上架"), DOWN_SPU(2,"商品下架");

        private final int code;
        private final String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
