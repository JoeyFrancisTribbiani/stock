package com.yy.stock.controller;

import com.yy.stock.common.result.Result;
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
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @PostMapping("/save")
    public Result<String> save(@Valid @RequestBody Platform vO) {
        return Result.success(platformService.save(vO).toString());
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

    @PostMapping("/list")
    public Result<Page<Platform>> query(@RequestBody PlatformQueryVO vO) {
        var pageble = platformService.query(vO);
        return Result.success(pageble);
    }

    @PostMapping("/allList")
    public Result<List<Platform>> list() {
        var pageble = platformService.allList();
        return Result.success(pageble);
    }
}
