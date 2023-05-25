package com.yy.stock.controller;

import com.yy.stock.common.result.Result;
import com.yy.stock.entity.AmazonSeller;
import com.yy.stock.service.AmazonSellerService;
import com.yy.stock.vo.AmazonSellerQueryVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/amazonSeller")
public class AmazonSellerController {

    @Autowired
    private AmazonSellerService amazonSellerService;

    @PostMapping("/save")
    public Result save(@Valid @RequestBody AmazonSeller vO) {
        amazonSellerService.save(vO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amazonSellerService.delete(id);
    }

    @PostMapping("/list")
    public Result<Page<AmazonSeller>> query(@Valid @RequestBody AmazonSellerQueryVO vO) {
        Page<AmazonSeller> page = amazonSellerService.query(vO);
        return Result.success(page);
    }
}
