package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.ShopDTO;
import com.yy.stock.adaptor.amazon.service.ShopService;
import com.yy.stock.adaptor.amazon.vo.ShopQueryVO;
import com.yy.stock.adaptor.amazon.vo.ShopUpdateVO;
import com.yy.stock.adaptor.amazon.vo.ShopVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public String save(@Valid @RequestBody ShopVO vO) {
        return shopService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        shopService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody ShopUpdateVO vO) {
        shopService.update(id, vO);
    }

    @GetMapping("/{id}")
    public ShopDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return shopService.getById(id);
    }

    @GetMapping
    public Page<ShopDTO> query(@Valid ShopQueryVO vO) {
        return shopService.query(vO);
    }
}
