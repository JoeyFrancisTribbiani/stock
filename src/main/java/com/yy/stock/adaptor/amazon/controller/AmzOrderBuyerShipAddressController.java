package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmzOrderBuyerShipAddressDTO;
import com.yy.stock.adaptor.amazon.service.AmzOrderBuyerShipAddressService;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amzOrderBuyerShipAddress")
public class AmzOrderBuyerShipAddressController {

    @Autowired
    private AmzOrderBuyerShipAddressService amzOrderBuyerShipAddressService;

    @PostMapping
    public String save(@Valid @RequestBody AmzOrderBuyerShipAddressVO vO) {
        return amzOrderBuyerShipAddressService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amzOrderBuyerShipAddressService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmzOrderBuyerShipAddressUpdateVO vO) {
        amzOrderBuyerShipAddressService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmzOrderBuyerShipAddressDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amzOrderBuyerShipAddressService.getById(id);
    }

    @GetMapping
    public Page<AmzOrderBuyerShipAddressDTO> query(@Valid AmzOrderBuyerShipAddressQueryVO vO) {
        return amzOrderBuyerShipAddressService.query(vO);
    }
}
