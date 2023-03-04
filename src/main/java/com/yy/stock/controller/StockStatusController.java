package com.yy.stock.controller;

import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/status")
public class StockStatusController {

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


}
