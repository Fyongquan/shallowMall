package com.fyq.shallowMall.ware;

import com.fyq.shallowMall.ware.entity.WareInfoEntity;
import com.fyq.shallowMall.ware.service.WareInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShallowMallWareApplicationTests {

    @Autowired
    private WareInfoService wareInfoService;

    @Test
    void contextLoads() {

        WareInfoEntity wareInfoEntity = new WareInfoEntity();
        wareInfoEntity.setName("华为");
        wareInfoEntity.setAddress("北京");
        wareInfoEntity.setAreacode("110000");

        wareInfoService.save(wareInfoEntity);
    }

}
