package com.yy.stock.controller;

import com.yy.stock.bot.factory.BotFactory;
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

@Validated
@RestController
@RequestMapping("/buyerAccount")
public class BuyerAccountController {

    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private BotFactory botFactory;

    @PostMapping
    public String save(@Valid @RequestBody BuyerAccount vO) {
        return buyerAccountService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") BigInteger id) {
        buyerAccountService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody BuyerAccount vO) {
        if (vO.getStatus().equals(BuyerStatusEnum.active.name())) {
            botFactory.removeBot(vO);
        }
        buyerAccountService.update(vO);
    }


    @GetMapping("/{id}")
    public BuyerAccount getById(@Valid @NotNull @PathVariable("id") BigInteger id) {
        return buyerAccountService.getById(id);
    }

    @GetMapping
    public Page<BuyerAccount> query(@Valid BuyerAccountQueryVO vO) {
        return buyerAccountService.query(vO);
    }
}
