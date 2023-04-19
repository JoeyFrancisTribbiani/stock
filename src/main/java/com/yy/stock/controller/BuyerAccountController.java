package com.yy.stock.controller;

import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.result.Result;
import com.yy.stock.dto.BuyerStatusEnum;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.vo.BuyerAccountQueryVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/buyerAccount")
public class BuyerAccountController {

    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private BotFactory botFactory;

    @PostMapping("/save")
    public Result<String> save(@Valid @RequestBody BuyerAccount vO) {
        return Result.success(buyerAccountService.save(vO).toString());
    }

    @PostMapping("delete")
//    @DeleteMapping
    public Result<String> batchDelete(@Valid @RequestBody List<BuyerAccount> list) {
        buyerAccountService.batchDelete(list);
        return Result.success("success");
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody BuyerAccount vO) {
        if (vO.getStatus().equals(BuyerStatusEnum.inactive.name())) {
            botFactory.removeBot(vO);
        }
        buyerAccountService.update(vO);
    }


    @GetMapping("/{id}")
    public BuyerAccount getById(@Valid @NotNull @PathVariable("id") BigInteger id) {
        return buyerAccountService.getById(id);
    }

    @PostMapping("/list")
    public Result<Page<BuyerAccount>> query(@Valid @RequestBody BuyerAccountQueryVO vO) {
        var pageble = buyerAccountService.query(vO);
        return Result.success(pageble);
    }
}