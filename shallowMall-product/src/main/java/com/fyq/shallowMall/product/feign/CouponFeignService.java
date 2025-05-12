package com.fyq.shallowMall.product.feign;

import com.fyq.common.to.MemberPriceTo;
import com.fyq.common.to.SkuFullReductionTo;
import com.fyq.common.to.SkuLadderTo;
import com.fyq.common.to.SpuBoundsTo;
import com.fyq.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("shallowMall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("/coupon/skufullreduction/save")
    R saveSkuFullReduction(@RequestBody SkuFullReductionTo skuFullReductionTo);

    @PostMapping("coupon/skuladder/save")
    R saveSkuLadder(@RequestBody SkuLadderTo skuLadderTo);

    @PostMapping("coupon/memberprice/saveBatch")
    R saveMemberPriceBatch(@RequestBody List<MemberPriceTo> memberPriceTo);
}
