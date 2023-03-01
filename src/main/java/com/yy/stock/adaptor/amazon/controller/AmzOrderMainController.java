package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmzOrderMainDTO;
import com.yy.stock.adaptor.amazon.service.AmzOrderMainService;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amzOrderMain")
public class AmzOrderMainController {

    @Autowired
    private AmzOrderMainService amzOrderMainService;

//    @GetMapping
//    public List<AmzOrderMainDTO> getAllUnshipedOrders() {
//        return amzOrderMainService.getById(id);
//    }
    @PostMapping
    public String save(@Valid @RequestBody AmzOrderMainVO vO) {
        return amzOrderMainService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amzOrderMainService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmzOrderMainUpdateVO vO) {
        amzOrderMainService.update(id, vO);
    }

//    @GetMapping("/{id}")
//    public AmzOrderMainDTO getById(@Valid @NotNull @PathVariable("id") String id) {
//        return amzOrderMainService.getById(id);
//    }

    @GetMapping
    public Page<AmzOrderMainDTO> query(@Valid AmzOrderMainQueryVO vO) {
        return amzOrderMainService.query(vO);
    }
}
