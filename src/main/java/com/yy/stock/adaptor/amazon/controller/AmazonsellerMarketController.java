package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmazonsellerMarketDTO;
import com.yy.stock.adaptor.amazon.service.AmazonsellerMarketService;
import com.yy.stock.adaptor.amazon.vo.AmazonsellerMarketQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmazonsellerMarketUpdateVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amazonsellerMarket")
public class AmazonsellerMarketController {

    @Autowired
    private AmazonsellerMarketService amazonsellerMarketService;


    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amazonsellerMarketService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmazonsellerMarketUpdateVO vO) {
        amazonsellerMarketService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmazonsellerMarketDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amazonsellerMarketService.getById(id);
    }

    @GetMapping
    public Page<AmazonsellerMarketDTO> query(@Valid AmazonsellerMarketQueryVO vO) {
        return amazonsellerMarketService.query(vO);
    }
}
