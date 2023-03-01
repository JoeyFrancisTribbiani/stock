package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmazonAuthDTO;
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
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amazonAuthService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmazonAuthUpdateVO vO) {
        amazonAuthService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmazonAuthDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amazonAuthService.getById(id);
    }

    @GetMapping
    public Page<AmazonAuthDTO> query(@Valid AmazonAuthQueryVO vO) {
        return amazonAuthService.query(vO);
    }
}
