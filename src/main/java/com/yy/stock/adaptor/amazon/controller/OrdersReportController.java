package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.adaptor.amazon.vo.OrdersReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/ordersReport")
public class OrdersReportController {

    @Autowired
    private OrdersReportService ordersReportService;

    @PostMapping
    public String save(@Valid @RequestBody OrdersReportVO vO) {
        return ordersReportService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        ordersReportService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody OrdersReportUpdateVO vO) {
        ordersReportService.update(id, vO);
    }

    @GetMapping("/{id}")
    public OrdersReportDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return ordersReportService.getById(id);
    }

    @GetMapping
    public Page<OrdersReportDTO> query(@Valid OrdersReportQueryVO vO) {
        return ordersReportService.query(vO);
    }
}
