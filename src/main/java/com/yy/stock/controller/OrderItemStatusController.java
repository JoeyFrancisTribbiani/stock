package com.yy.stock.controller;

import com.yy.stock.dto.OrderItemStatusDTO;
import com.yy.stock.service.OrderItemStatusService;
import com.yy.stock.vo.OrderItemStatusQueryVO;
import com.yy.stock.vo.OrderItemStatusUpdateVO;
import com.yy.stock.vo.OrderItemStatusVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/orderItemStatus")
public class OrderItemStatusController {

    @Autowired
    private OrderItemStatusService orderItemStatusService;

    @PostMapping
    public String save(@Valid @RequestBody OrderItemStatusVO vO) {
        return orderItemStatusService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        orderItemStatusService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody OrderItemStatusUpdateVO vO) {
        orderItemStatusService.update(id, vO);
    }

    @GetMapping("/{id}")
    public OrderItemStatusDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return orderItemStatusService.getById(id);
    }

    @GetMapping
    public Page<OrderItemStatusDTO> query(@Valid OrderItemStatusQueryVO vO) {
        return orderItemStatusService.query(vO);
    }
}
