package com.fyq.shallowMall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fyq.shallowMall.product.entity.BrandEntity;
import com.fyq.shallowMall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShallowMallProductApplicationTests {

    @Autowired
    private BrandService brandService;

//    @Autowired
//    private OSSClient ossClient;
//
//    @Test
//    public void testUpload() throws com.aliyuncs.exceptions.ClientException, FileNotFoundException {
//        InputStream inputStream = new FileInputStream("C:\\Users\\付yq\\Pictures\\Screenshots\\屏幕截图 2024-07-01 015036.png");
//        ossClient.putObject("shallow-mall", "fyq.png", inputStream);
//        System.out.println("上传完成");
//    }

    @Test
    public void contextLoads() {
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        System.out.println("返回的结果是" + list);
    }

}
