package com.fyq.shallowMall.product.feign;

import com.fyq.common.to.es.SkuEsModel;
import com.fyq.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("shallowMall-search")
public interface SearchFeignService {

    /**
     * 将skus添加到es
     * @param skuEsModels
     * @return
     */
    @PostMapping("/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
