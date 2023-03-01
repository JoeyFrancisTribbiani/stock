package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.OrdersFulfilledShipmentsReportDTO;
import com.yy.stock.adaptor.amazon.service.OrdersFulfilledShipmentsReportService;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/ordersFulfilledShipmentsReport")
public class OrdersFulfilledShipmentsReportController {

    @Autowired
    private OrdersFulfilledShipmentsReportService ordersFulfilledShipmentsReportService;

    @PostMapping
    public String save(@Valid @RequestBody OrdersFulfilledShipmentsReportVO vO) {
        return ordersFulfilledShipmentsReportService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        ordersFulfilledShipmentsReportService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody OrdersFulfilledShipmentsReportUpdateVO vO) {
        ordersFulfilledShipmentsReportService.update(id, vO);
    }

    @GetMapping("/{id}")
    public OrdersFulfilledShipmentsReportDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return ordersFulfilledShipmentsReportService.getById(id);
    }

    @GetMapping
    public Page<OrdersFulfilledShipmentsReportDTO> query(@Valid OrdersFulfilledShipmentsReportQueryVO vO) {
        return ordersFulfilledShipmentsReportService.query(vO);
    }
}
