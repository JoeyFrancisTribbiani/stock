package com.yy.stock.controller;

import com.yy.stock.entity.Supplier;
import com.yy.stock.service.SupplierService;
import com.yy.stock.vo.SupplierQueryVO;
import com.yy.stock.vo.SupplierUpdateVO;
import com.yy.stock.vo.SupplierVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Validated
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public String save(@Valid @RequestBody SupplierVO vO) {
        return supplierService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") BigInteger id) {
        supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") BigInteger id,
                       @Valid @RequestBody SupplierUpdateVO vO) {
        supplierService.update(id, vO);
    }

    @GetMapping("/{id}")
    public Supplier getById(@Valid @NotNull @PathVariable("id") BigInteger id) {
        return supplierService.getById(id);
    }

    @GetMapping
    public Page<Supplier> query(@Valid SupplierQueryVO vO) {
        return supplierService.query(vO);
    }
}
