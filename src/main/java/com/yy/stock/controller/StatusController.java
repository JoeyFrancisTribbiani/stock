package com.yy.stock.controller;

import com.yy.stock.dto.StatusDTO;
import com.yy.stock.entity.Status;
import com.yy.stock.service.StatusService;
import com.yy.stock.vo.StatusQueryVO;
import com.yy.stock.vo.StatusUpdateVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public String save(@Valid @RequestBody Status vO) {
        return statusService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        statusService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody StatusUpdateVO vO) {
        statusService.update(id, vO);
    }

    @GetMapping("/{id}")
    public StatusDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return statusService.getById(id);
    }

    @GetMapping
    public Page<StatusDTO> query(@Valid StatusQueryVO vO) {
        return statusService.query(vO);
    }
}
