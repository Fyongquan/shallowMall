package com.fyq.shallowMall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShallowMallProductApplicationTests {

    @Autowired
    private BrandService brandService;
    @Test
    void contextLoads() {

//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1L);
//        brandEntity.setName("你离");
////        brandEntity.setDescript("华为手机");
////        brandEntity.setBrandId(1L);
////        brandEntity.setLogo("https://www.baidu.com");
////        brandEntity.setShowStatus(1);
////        brandEntity.setFirstLetter("H");
////        brandService.save(brandEntity);
////        System.out.println("保存成功");
//
//        brandService.updateById(brandEntity);

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        System.out.println("返回的结果是" + list);
    }

}
