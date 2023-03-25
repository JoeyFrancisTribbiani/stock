package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.entity.AmazonAuth;
import com.yy.stock.adaptor.amazon.service.AmazonAuthService;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amazonAuth")
public class AmazonAuthController {

    @Autowired
    private AmazonAuthService amazonAuthService;

    @PostMapping
    public String save(@Valid @RequestBody AmazonAuthVO vO) {
        return amazonAuthService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        amazonAuthService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody AmazonAuthUpdateVO vO) {
        amazonAuthService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmazonAuth getById(@Valid @NotNull @PathVariable("id") Long id) {
        return amazonAuthService.getById(id);
    }

    @GetMapping
    public Page<AmazonAuth> query(@Valid AmazonAuthQueryVO vO) {
        return amazonAuthService.query(vO);
    }
}
