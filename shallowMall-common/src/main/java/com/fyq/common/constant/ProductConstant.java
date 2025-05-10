package com.fyq.common.constant;

import lombok.Getter;

public class ProductConstant {

    @Getter
    public enum AttrEnum{
        ATTR_TYPE_BASE(1, "base"),ATTR_TYPE_SALE(0,"sale");

        private final int code;
        private final String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
