package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmzOrderItemDTO;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amzOrderItem")
public class AmzOrderItemController {

    @Autowired
    private AmzOrderItemService amzOrderItemService;

    @PostMapping
    public String save(@Valid @RequestBody AmzOrderItemVO vO) {
        return amzOrderItemService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amzOrderItemService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmzOrderItemUpdateVO vO) {
        amzOrderItemService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmzOrderItemDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amzOrderItemService.getById(id);
    }

    @GetMapping
    public Page<AmzOrderItemDTO> query(@Valid AmzOrderItemQueryVO vO) {
        return amzOrderItemService.query(vO);
    }
}
