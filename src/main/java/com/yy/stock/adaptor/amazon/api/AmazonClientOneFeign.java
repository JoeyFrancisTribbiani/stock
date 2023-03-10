package com.yy.stock.adaptor.amazon.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(value = "wimoor-amazon")
public interface AmazonClientOneFeign {

    @PostMapping("/amazon/api/v1/report/product/productInfo/productList")
    public Result<Page<AmzProductListVo>> getProductListAction(@RequestBody ProductListQuery query);

    @GetMapping("/amazon/api/v1/shipFormSync/confirmSyncShipment")
    public Result<?> confirmSyncShipment(@RequestParam String shipmentid);

    @GetMapping("/amazon/api/v1/product/salesplan/getPlanModelItem")
    public Result<List<Map<String, Object>>> getPlanItem(@RequestParam String groupid);
}