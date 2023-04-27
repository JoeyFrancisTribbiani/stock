package com.yy.stock.controller;

import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.service.AmazonSelectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/amazonSelection")
public class AmazonSelectionController {

    @Autowired
    private AmazonSelectionService amazonSelectionService;

    @GetMapping
    public Page<AmazonSelection> query(@Valid AmazonSelection vO) {
        return amazonSelectionService.query(vO);
    }
}
