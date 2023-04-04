package com.yy.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.result.Result;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.entity.Supplier;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {
    @Autowired
    private BotFactory botFactory;
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
    public Result<String> getSupplierSkuProperties(@RequestParam("url") String url, @RequestParam("countryCode") String countryCode) throws IOException, InterruptedException, MessagingException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (url == null) {
            throw new IllegalArgumentException("url 不能为空");
        }
        var platform = PlatformFactory.getPlatformByUrl(url, countryCode);
        var buyerAccount = buyerAccountService.getEarliestLoginedIdleBuyer(platform.getId());
        var bot = botFactory.getBot(buyerAccount);
        var html = bot.fetch(url);
        if (Objects.equals(html, GlobalVariables.PRODUCT_PAGE_NOT_FOUND)) {
            return Result.failed(GlobalVariables.PRODUCT_PAGE_NOT_FOUND);
        }
        if (Objects.equals(html, "")) {
            return Result.failed("抓取失败！请检查链接！");
        }

        return Result.success(html);
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
