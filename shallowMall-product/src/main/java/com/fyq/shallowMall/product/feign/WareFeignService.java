package com.fyq.shallowMall.product.feign;

import com.fyq.common.to.SkuHasStockTo;
import com.fyq.common.utils.R;
import com.fyq.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("shallowMall-ware")
public interface WareFeignService {

    @PostMapping("/ware/waresku/hasStock")
    Result<List<SkuHasStockTo>> hasStock(@RequestBody List<Long> skuIds);
}
