package com.fyq.shallowMall.ware;

import com.fyq.shallowMall.ware.entity.WareInfoEntity;
import com.fyq.shallowMall.ware.service.WareInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShallowMallWareApplication.class)
public class ShallowMallWareApplicationTests {

    @Autowired
    private WareInfoService wareInfoService;

    @Test
    public void contextLoads() {
        WareInfoEntity wareInfoEntity = new WareInfoEntity();
        wareInfoEntity.setName("华为");
        wareInfoEntity.setAddress("北京");
        wareInfoEntity.setAreacode("110000");

        wareInfoService.save(wareInfoEntity);
    }

}
