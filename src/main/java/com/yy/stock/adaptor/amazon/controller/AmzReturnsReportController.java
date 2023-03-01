package com.yy.stock.adaptor.amazon.controller;

import com.yy.stock.adaptor.amazon.dto.AmzReturnsReportDTO;
import com.yy.stock.adaptor.amazon.service.AmzReturnsReportService;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/amzReturnsReport")
public class AmzReturnsReportController {

    @Autowired
    private AmzReturnsReportService amzReturnsReportService;

    @PostMapping
    public String save(@Valid @RequestBody AmzReturnsReportVO vO) {
        return amzReturnsReportService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        amzReturnsReportService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody AmzReturnsReportUpdateVO vO) {
        amzReturnsReportService.update(id, vO);
    }

    @GetMapping("/{id}")
    public AmzReturnsReportDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return amzReturnsReportService.getById(id);
    }

    @GetMapping
    public Page<AmzReturnsReportDTO> query(@Valid AmzReturnsReportQueryVO vO) {
        return amzReturnsReportService.query(vO);
    }
}
