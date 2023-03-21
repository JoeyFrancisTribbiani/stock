package com.yy.stock.adaptor.amazon.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.stock.adaptor.amazon.api.pojo.dto.AmazonOrdersListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmazonOrdersVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(value = "wimoor-amazon")
public interface AmazonClientOneFeign {

    //    @PostMapping("/amazon/api/v1/report/product/productInfo/productList")
    @RequestMapping(value = "/amazon/api/v1/report/product/productInfo/productList", method = RequestMethod.POST)
    public Result<Page<AmzProductListVo>> getProductListAction(@RequestBody ProductListQuery query);

    @RequestMapping(value = "/amazon/api/v0/orders/manager/list", method = RequestMethod.POST)
    public Result<Page<AmazonOrdersVo>> getOrderListAction(@RequestBody AmazonOrdersListQuery query);

    @RequestMapping(value = "/amazon/api/v0/orders/invoince/list", method = RequestMethod.POST)
    public Result<Page<AmazonOrdersVo>> getOrderInvoiceListAction(@RequestBody AmazonOrdersListQuery query);

    @GetMapping("/amazon/api/v1/shipFormSync/confirmSyncShipment")
    public Result<?> confirmSyncShipment(@RequestParam String shipmentid);

    @GetMapping("/amazon/api/v1/product/salesplan/getPlanModelItem")
    public Result<List<Map<String, Object>>> getPlanItem(@RequestParam String groupid);
}