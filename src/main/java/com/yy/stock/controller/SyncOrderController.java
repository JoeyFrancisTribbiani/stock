package com.yy.stock.controller;

import com.yy.stock.entity.SyncOrder;
import com.yy.stock.service.SyncOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/syncOrder")
public class SyncOrderController {

    @Autowired
    private SyncOrderService syncOrderService;

    @PostMapping
    public String save(@Valid @RequestBody SyncOrder vO) {
        return syncOrderService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        syncOrderService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody SyncOrder vO) {
        syncOrderService.update(id, vO);
    }

    @GetMapping("/{id}")
    public SyncOrder getById(@Valid @NotNull @PathVariable("id") Long id) {
        return syncOrderService.getById(id);
    }

    @GetMapping
    public Page<SyncOrder> query(@Valid SyncOrder vO) {
        return syncOrderService.query(vO);
    }
}
