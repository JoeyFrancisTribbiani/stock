package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.ErpShipStatusDTO;
import com.yy.stock.adaptor.amazon.service.ErpShipStatusService;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusQueryVO;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusUpdateVO;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/erpShipStatus")
public class ErpShipStatusController {

    @Autowired
    private ErpShipStatusService erpShipStatusService;

    @PostMapping
    public String save(@Valid @RequestBody ErpShipStatusVO vO) {
        return erpShipStatusService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        erpShipStatusService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody ErpShipStatusUpdateVO vO) {
        erpShipStatusService.update(id, vO);
    }

    @GetMapping("/{id}")
    public ErpShipStatusDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return erpShipStatusService.getById(id);
    }

    @GetMapping
    public Page<ErpShipStatusDTO> query(@Valid ErpShipStatusQueryVO vO) {
        return erpShipStatusService.query(vO);
    }
}
