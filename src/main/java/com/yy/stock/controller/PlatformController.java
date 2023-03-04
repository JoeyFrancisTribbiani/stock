package com.yy.stock.controller;

import com.yy.stock.entity.Platform;
import com.yy.stock.service.PlatformService;
import com.yy.stock.vo.PlatformQueryVO;
import com.yy.stock.vo.PlatformUpdateVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Validated
@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @PostMapping
    public String save(@Valid @RequestBody Platform vO) {
        return platformService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") BigInteger id) {
        platformService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") BigInteger id,
                       @Valid @RequestBody PlatformUpdateVO vO) {
        platformService.update(id, vO);
    }

    @GetMapping("/{id}")
    public Platform getById(@Valid @NotNull @PathVariable("id") BigInteger id) {
        return platformService.getById(id);
    }

    @GetMapping
    public Page<Platform> query(@Valid PlatformQueryVO vO) {
        return platformService.query(vO);
    }
}
