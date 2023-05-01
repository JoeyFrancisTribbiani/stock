package com.yy.stock.controller;

import com.yy.stock.service.AmazonCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/amazonCategory")
public class AmazonCategoryController {

    @Autowired
    private AmazonCategoryService amazonCategoryService;

}
