package com.yy.stock.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.amazon.api.AmazonClientOneFeign;
import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.adaptor.amazon.api.pojo.dto.AmazonOrdersListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmazonOrdersVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockOrderStatusListVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;
import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.result.Result;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.Supplier;
import com.yy.stock.scheduler.StockAsyncExecutor;
import com.yy.stock.service.BuyerAccountService;
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
    private AmzOrdersAddressService amzOrdersAddressService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private BotFactory botFactory;

    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
    @Autowired
    private AmzOrderItemService amzOrderItemService;
    @Autowired
    private StockAsyncExecutor stockAsyncExecutor;

    @PostMapping
    public String save(@Valid @RequestBody StockStatus vO) {
        return stockStatusService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") BigInteger id) {
        stockStatusService.delete(id);
    }

    @PostMapping("/stockStatusList")
    public Result<Page<StockStatusListVo>> productListAction(@RequestBody ProductListQuery query) throws JsonProcessingException {
        Result<Page<AmzProductListVo>> result = amazonClientOneFeign.getProductListAction(query);
        if (Result.isSuccess(result) && result.getData() != null) {
            var gotPage = result.getData();
            List<StockStatusListVo> list = new ArrayList<>();
//            for (var product : result.getData().getRecords()) {

            var product0 = result.getData().getRecords().get(0);
            var authId = product0.getAmazonAuthId();
            var marketplaceId = product0.getMarketplaceid();

            var skuList = result.getData().getRecords().stream().map(AmzProductListVo::getSku).toList();
            var supplierList = supplierService.getBySkus(new BigInteger(authId), marketplaceId, skuList);

            for (int i = 0; i < result.getData().getRecords().size(); i++) {
                var product = result.getData().getRecords().get(i);
                var supplier = supplierList.stream().filter(s -> s.getAmazonSku().equals(product.getSku())).findFirst().orElse(null);

                StockStatusListVo vo = new StockStatusListVo();
                BeanUtils.copyProperties(product, vo);

                Platform platform = null;
                if (supplier == null) {
                    supplier = new Supplier();
                    platform = new Platform();
                } else {
                    platform = supplier.getPlatform();
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
    }

    @PostMapping("/orderStatusList")
    public Result<Page<StockOrderStatusListVo>> orderListAction(@RequestBody AmazonOrdersListQuery query) throws JsonProcessingException {
        Result<Page<AmazonOrdersVo>> result = amazonClientOneFeign.getOrderListAction(query);
        if (Result.isSuccess(result) && result.getData() != null && result.getData().getRecords().size() != 0) {
            var gotPage = result.getData();
            List<StockOrderStatusListVo> list = new ArrayList<>();
            var order1 = result.getData().getRecords().get(0);
            var skuList = result.getData().getRecords().stream().map(AmazonOrdersVo::getSku).toList();
            var orderIdList = result.getData().getRecords().stream().map(AmazonOrdersVo::getOrderid).toList();
            var authId = order1.getAuthid();
            var marketplaceId = order1.getMarketplaceId();

            var supplierList = supplierService.getBySkus(new BigInteger(order1.getAuthid()), order1.getMarketplaceId(), skuList);
            var addressList = amzOrdersAddressService.getByOrderIdList(new BigInteger(authId), marketplaceId, orderIdList);
            var stockStatusList = stockStatusService.getByAmazonOrderList(new BigInteger(authId), marketplaceId, result.getData().getRecords());

            //改为fori
            for (int i = 0; i < result.getData().getRecords().size(); i++) {
                var orderVo = result.getData().getRecords().get(i);
                StockOrderStatusListVo vo = new StockOrderStatusListVo();
                BeanUtils.copyProperties(orderVo, vo);
                authId = orderVo.getAuthid();
                var sku = orderVo.getSku();
                marketplaceId = orderVo.getMarketplaceId();
//                var supplier = supplierService.getBySku(new BigInteger(authId), marketplaceId, sku);
                var supplier = supplierList.stream().filter(s -> s.getAmazonSku().equals(sku)).findFirst().orElse(null);

                Platform platform;
                if (supplier == null) {
                    supplier = new Supplier();
                    platform = new Platform();
                } else {
                    platform = supplier.getPlatform();
                }

//                var stockStatus = stockStatusService.getOrCreateByOrderItemSku(new BigInteger(authId), marketplaceId, orderVo.getOrderid(), orderVo.getSku());
                var stockStatus = stockStatusList.stream().filter(s -> s.getAmazonOrderId().equals(orderVo.getOrderid()) && s.getAmazonSku().equals(orderVo.getSku())).findFirst().orElse(null);
                BuyerAccount buyerAccount = null;
                if (stockStatus != null) {
                    if (stockStatus.getBuyer() != null) {
                        buyerAccount = stockStatus.getBuyer();
                    } else {
                        buyerAccount = new BuyerAccount();
                    }
                } else {
                    stockStatus = new StockStatus();
                }
//                var address = amzOrdersAddressService.getByOrderInfo(new BigInteger(authId), marketplaceId, orderVo.getOrderid());
                var address = addressList.stream().filter(a -> a.getAmazonOrderId().equals(orderVo.getOrderid())).findFirst().orElse(null);
                if (address == null) {
                    address = new AmzOrdersAddress();
                }
                vo.setSupplier(supplier);
                vo.setPlatform(platform);
                vo.setBuyerAccount(buyerAccount);
                vo.setStockStatus(stockStatus);
                vo.setAddress(address);
                list.add(vo);
            }
            var page = new Page<StockOrderStatusListVo>(gotPage.getCurrent(), gotPage.getSize(), gotPage.getTotal(), gotPage.searchCount());
            page.setOrders(gotPage.getOrders());
            page.setPages(gotPage.getPages());
            page.setRecords(list);
            return Result.success(page);
        }
        var page = new Page<StockOrderStatusListVo>(0, 0, 0, false);
        return Result.success(page);
    }

    @PostMapping("/saveOrderStockStatus")
    public Result<Boolean> saveOrderStockStatusAction(@RequestBody StockStatus stockStatus) throws JsonProcessingException {
        try {
            stockStatusService.save(stockStatus);
            return Result.success(true);
        } catch (Exception ex) {
            return Result.failed();
        }
    }

    @PostMapping("/manualStock")
    public Result<Boolean> manualStockAction(@RequestBody StockStatus stockStatus) throws JsonProcessingException {
        try {
            var orderItemInfo = amzOrderItemService.findInfoByItemId(stockStatus.getAmazonOrderId(), stockStatus.getOrderItemId());
            stockAsyncExecutor.startStockAsync(orderItemInfo, stockStatus);
            return Result.success(true);
        } catch (Exception ex) {
            return Result.failed(ex.getMessage());
        }
    }

    @PostMapping("/postShipTrackNumber2Amazon")
    public Result<Boolean> postShipTrackNumber2AmazonAction(@RequestBody StockStatus stockStatus) throws JsonProcessingException {
        try {
            orderFulfillmentSubmitFeedService.submit(stockStatus);
            stockStatus.setRemarks("已人工提交跟踪号:" + stockStatus.getShipmentTrackNumber());
            stockStatus.setShipmentTrackNumber("");
            stockStatusService.save(stockStatus);
            return Result.success(true);
        } catch (Exception ex) {
            return Result.failed();
        }
    }

    @PostMapping("/reTrackShipmentNumber")
    public Result<Boolean> reTrackShipmentNumberAction(@RequestBody StockStatus stockStatus) throws JsonProcessingException {
        try {
            var buyer = buyerAccountService.getTrackableBuyerAccount(stockStatus.getBuyer().getId());
            var bot = botFactory.getBot(buyer);
            var trackRequest = new TrackRequest(stockStatus);
            bot.track(trackRequest);
            return Result.success(true);
        } catch (Exception ex) {
            return Result.failed();
        }
    }

    @PostMapping("/orderInvoiceStatusList")
    public Result<Page<StockOrderStatusListVo>> orderInvoiceListAction(@RequestBody AmazonOrdersListQuery query) throws JsonProcessingException {
        Result<Page<AmazonOrdersVo>> result = amazonClientOneFeign.getOrderListAction(query);
        if (Result.isSuccess(result) && result.getData() != null) {
            var gotPage = result.getData();
            List<StockOrderStatusListVo> list = new ArrayList<>();
            for (var orderVo : result.getData().getRecords()) {
                StockOrderStatusListVo vo = new StockOrderStatusListVo();
                BeanUtils.copyProperties(orderVo, vo);
                var authId = orderVo.getAuthid();
                var sku = orderVo.getSku();
                var marketplaceId = orderVo.getMarketplaceId();
                var supplier = supplierService.getBySku(new BigInteger(authId), marketplaceId, sku);

                Platform platform;
                if (supplier == null) {
                    supplier = new Supplier();
                    platform = new Platform();
                } else {
                    platform = supplier.getPlatform();
                }
                var stockStatus = stockStatusService.getOrCreateByOrderItemSku(new BigInteger(authId), marketplaceId, orderVo.getOrderid(), orderVo.getSku());
                BuyerAccount buyerAccount = null;
                if (stockStatus != null) {
                    buyerAccount = stockStatus.getBuyer();
                } else {
                    stockStatus = new StockStatus();
                    buyerAccount = new BuyerAccount();
                }
                vo.setSupplier(supplier);
                vo.setPlatform(platform);
                vo.setBuyerAccount(buyerAccount);
                vo.setStockStatus(stockStatus);
                list.add(vo);
            }
            var page = new Page<StockOrderStatusListVo>(gotPage.getCurrent(), gotPage.getSize(), gotPage.getTotal(), gotPage.searchCount());
            page.setOrders(gotPage.getOrders());
            page.setPages(gotPage.getPages());
            page.setRecords(list);
            return Result.success(page);

        }
        return Result.failed();
    }


}
