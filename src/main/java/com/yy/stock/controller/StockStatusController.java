package com.yy.stock.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.stock.adaptor.amazon.api.AmazonClientOneFeign;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.common.result.Result;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/stock")
public class StockStatusController {

    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;
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
    public Result<IPage<AmzProductListVo>> productListAction(@RequestBody ProductListQuery query) {
        Result<IPage<AmzProductListVo>> result = amazonClientOneFeign.getProductListAction(query);
        if (Result.isSuccess(result) && result.getData() != null) {

        }
        return result;
    }

}
