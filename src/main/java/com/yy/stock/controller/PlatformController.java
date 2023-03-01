package com.yy.stock.controller;

import com.yy.stock.dto.PlatformDTO;
import com.yy.stock.service.PlatformService;
import com.yy.stock.vo.PlatformQueryVO;
import com.yy.stock.vo.PlatformUpdateVO;
import com.yy.stock.vo.PlatformVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @PostMapping
    public String save(@Valid @RequestBody PlatformVO vO) {
        return platformService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        platformService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody PlatformUpdateVO vO) {
        platformService.update(id, vO);
    }

    @GetMapping("/{id}")
    public PlatformDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return platformService.getById(id);
    }

    @GetMapping
    public Page<PlatformDTO> query(@Valid PlatformQueryVO vO) {
        return platformService.query(vO);
    }
}
