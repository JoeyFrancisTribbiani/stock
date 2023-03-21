package com.yy.stock.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.adaptor.amazon.api.AmazonClientOneFeign;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;
import com.yy.stock.common.result.Result;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.Supplier;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StockStatusService;
import com.yy.stock.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/stockStatus")
public class StockStatusController {

    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private StockStatusService stockStatusService;

    @PostMapping
    public String save(@Valid @RequestBody StockStatus vO) {
        return stockStatusService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        stockStatusService.delete(id);
    }

    @PostMapping("/stockStatusList")
    public Result<Page<StockStatusListVo>> productListAction(@RequestBody ProductListQuery query) throws JsonProcessingException {
        var queryStr = new ObjectMapper().writeValueAsString(query);
//        System.out.println(queryStr);
        Result<Page<AmzProductListVo>> result = amazonClientOneFeign.getProductListAction(query);
        if (Result.isSuccess(result) && result.getData() != null) {
//            var stockResult = new Result<Page<StockStatusListVo>>();
//            BeanUtils.copyProperties(result, stockResult);
            var gotPage = result.getData();
            List<StockStatusListVo> list = new ArrayList<>();
            for (var product : result.getData().getRecords()) {
                StockStatusListVo vo = new StockStatusListVo();
                BeanUtils.copyProperties(product, vo);
                var authId = product.getAmazonAuthId();
                var sku = product.getSku();
                var marketplaceId = product.getMarketplaceid();
                var supplier = supplierService.getBySku(new BigInteger(authId), marketplaceId, sku);

                Platform platform;
                if (supplier == null) {
//                    supplier = supplierService.createEmptySupplier(new BigInteger(authId), marketplaceId, sku);
                    supplier = new Supplier();
                    platform = new Platform();
                } else {
                    platform = platformService.getById(supplier.getPlatformId());
                }
                vo.setSupplier(supplier);
                vo.setPlatform(platform);
                list.add(vo);
            }
            var page = new Page<StockStatusListVo>(gotPage.getCurrent(), gotPage.getSize(), gotPage.getTotal(), gotPage.searchCount());
            page.setOrders(gotPage.getOrders());
            page.setPages(gotPage.getPages());
            page.setRecords(list);
            return Result.success(page);

        }
        return Result.failed();
//        return result;
    }

}
