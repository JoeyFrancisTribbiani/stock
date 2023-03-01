package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amzOrdersAddress")
public class AmzOrdersAddressController {

    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;

    @PostMapping
    public String save(@Valid @RequestBody AmzOrdersAddressVO vO) {
        return amzOrdersAddressService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amzOrdersAddressService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmzOrdersAddressUpdateVO vO) {
        amzOrdersAddressService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmzOrdersAddressDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amzOrdersAddressService.getById(id);
    }

    @GetMapping
    public Page<AmzOrdersAddressDTO> query(@Valid AmzOrdersAddressQueryVO vO) {
        return amzOrdersAddressService.query(vO);
    }
}
