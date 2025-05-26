package com.fyq.shallowmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.fyq.common.to.es.SkuEsModel;
import com.fyq.shallowmall.search.config.ShallowMallElasticSearchConfig;
import com.fyq.shallowmall.search.constant.EsConstant;
import com.fyq.shallowmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        //保存到es
        //1.给es中建立索引，product。建立好映射关系

        //2.给es中保存这些数据
        //BulkRequest bulkRequest, RequestOptions options
        //RequestOptions都在config里写好了
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            //1、构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            //id
            indexRequest.id(skuEsModel.getSkuId().toString());
            //内容
            indexRequest.source(JSON.toJSONString(skuEsModel), XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ShallowMallElasticSearchConfig.COMMON_OPTIONS);

        //TODO 如果批量错误可以返回错误继续重试
        boolean hasFailures = bulk.hasFailures();
        List<String> list = Arrays.stream(bulk.getItems())
                .map(BulkItemResponse::getId)
                .collect(Collectors.toList());
        log.info("商品批量上架完成：{}", list);

        return !hasFailures;
    }

}
