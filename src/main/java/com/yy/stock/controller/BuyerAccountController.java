package com.yy.stock.controller;

import com.yy.stock.dto.BuyerAccountDTO;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.vo.BuyerAccountQueryVO;
import com.yy.stock.vo.BuyerAccountUpdateVO;
import com.yy.stock.vo.BuyerAccountVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/buyerAccount")
public class BuyerAccountController {

    @Autowired
    private BuyerAccountService buyerAccountService;

    @PostMapping
    public String save(@Valid @RequestBody BuyerAccountVO vO) {
        return buyerAccountService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        buyerAccountService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody BuyerAccountUpdateVO vO) {
        buyerAccountService.update(id, vO);
    }

    @GetMapping("/{id}")
    public BuyerAccountDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return buyerAccountService.getById(id);
    }

    @GetMapping
    public Page<BuyerAccountDTO> query(@Valid BuyerAccountQueryVO vO) {
        return buyerAccountService.query(vO);
    }
}
