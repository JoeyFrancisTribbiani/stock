package com.yy.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;
import com.yy.stock.common.result.Result;
import com.yy.stock.entity.Supplier;
import com.yy.stock.scheduler.BotFactory;
import com.yy.stock.scheduler.PlatformFactory;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.SupplierService;
import com.yy.stock.vo.SupplierQueryVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;

@Validated
@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private PlatformService platformService;

    @PostMapping
    public String save(@Valid @RequestBody Supplier vO) {
        return supplierService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") BigInteger id) {
        supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") BigInteger id,
                       @Valid @RequestBody Supplier vO) {
        supplierService.update(id, vO);
    }

    @GetMapping("/{id}")
    public Supplier getById(@Valid @NotNull @PathVariable("id") BigInteger id) {
        return supplierService.getById(id);
    }

    @GetMapping("/skuProperties")
    public Result<String> getSupplierSkuProperties(@RequestParam("url") String url, @RequestParam("countryCode") String countryCode) throws IOException, InterruptedException {

        if (url == null) {
            throw new IllegalArgumentException("url 不能为空");
        }
        var platform = PlatformFactory.getPlatformByUrl(url, countryCode);
        var buyerAccount = buyerAccountService.getLatestLoginedBuyer(platform.getId());
        var bot = BotFactory.getBot(platform, buyerAccount);
        var html = bot.getProductHtmlSource(url);
        var jsonStr = bot.getSkuProperties(html);
        bot.quit();
        var platfromJsonStr = new ObjectMapper().writeValueAsString(platform);
        platfromJsonStr = "\"platform\":" + platfromJsonStr + ",";
        var sb = new StringBuilder(jsonStr);
        var i = jsonStr.indexOf("{");
        sb.insert(i + 1, platfromJsonStr);
        jsonStr = sb.toString();
        return Result.success(jsonStr);
    }

    @PostMapping("/bindSku")
    public Result<StockStatusListVo> bindSkuAction(@RequestBody StockStatusListVo vo) throws JsonProcessingException {
        var supplier = supplierService.getBySku(new BigInteger(vo.getAmazonAuthId()), vo.getMarketplaceid(), vo.getSku());
        try {
            if (supplier == null) {
                supplier = vo.getSupplier();
                supplier.setAmazonSku(vo.getSku())
                        .setAmazonAuthId(new BigInteger(vo.getAmazonAuthId()))
                        .setPlatformId(vo.getPlatform().getId())
                        .setMarketplaceId(vo.getMarketplaceid())
                        .setAmazonName(vo.getName())
                        .setAmazonAsin(vo.getAsin());
                supplierService.save(supplier);
            } else {
                supplierService.update(supplier.getId(), vo.getSupplier());
            }
        } catch (Exception ex) {
            return Result.failed("出错了");
        }
        vo.setSupplier(supplier);
        return Result.success(vo);
    }

    @GetMapping
    public Page<Supplier> query(@Valid SupplierQueryVO vO) {
        return supplierService.query(vO);
    }
}
