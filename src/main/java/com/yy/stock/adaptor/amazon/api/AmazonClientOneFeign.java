package com.yy.stock.adaptor.amazon.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.stock.adaptor.amazon.api.pojo.dto.AmazonOrdersListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmazonOrdersVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.adaptor.amazon.dto.SubmitFeedRequest;
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
    Result<Page<AmzProductListVo>> getProductListAction(@RequestBody ProductListQuery query);

    @RequestMapping(value = "/amazon/api/v0/orders/manager/list", method = RequestMethod.POST)
    Result<Page<AmazonOrdersVo>> getOrderListAction(@RequestBody AmazonOrdersListQuery query);

    @RequestMapping(value = "/amazon/api/v0/orders/invoince/list", method = RequestMethod.POST)
    Result<Page<AmazonOrdersVo>> getOrderInvoiceListAction(@RequestBody AmazonOrdersListQuery query);

    @GetMapping("/amazon/api/v0/feed/submit")
    Result<?> submitFeed(@RequestBody SubmitFeedRequest request);

    @GetMapping("/amazon/api/v0/feed/process")
    Result<?> processSubmitFeed();

    @GetMapping("/amazon/api/v0/feed/submitfeedInfo")
    Result<?> getSubmitFeedInfo(@RequestParam String queueid);

    @GetMapping("/amazon/api/v0/orders/refreshOrderInDays/{days}")
    Result<?> refreshOrderInDays(@PathVariable int days);

    @GetMapping("/amazon/api/v0/orders/refreshAllOrderInDays/{days}")
    Result<?> refreshAllOrdersInDays(@PathVariable int days);

    @GetMapping("/amazon/api/v1/report/requestReport/{type}")
    Result<?> refreshOrderReport7Days(@PathVariable String type);


    @GetMapping("/amazon/api/v0/orders/getOrderItemInfo")
    Result<?> getOrderItemInfo(@RequestParam String orderId);

    @GetMapping("/amazon/api/v1/report/summaryOrderReport")
    Result<?> summaryOrderReport();

    @GetMapping("/amazon/api/v1/shipFormSync/confirmSyncShipment")
    Result<?> confirmSyncShipment(@RequestParam String shipmentid);

    @GetMapping("/amazon/api/v1/product/salesplan/getPlanModelItem")
    Result<List<Map<String, Object>>> getPlanItem(@RequestParam String groupid);
}