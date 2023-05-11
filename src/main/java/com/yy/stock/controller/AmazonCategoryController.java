package com.yy.stock.controller;

import com.yy.stock.common.result.Result;
import com.yy.stock.entity.AmazonCategory;
import com.yy.stock.scheduler.SelectAmazonProductScheduler;
import com.yy.stock.service.AmazonCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/amazonCategory")
public class AmazonCategoryController {

    @Autowired
    private AmazonCategoryService amazonCategoryService;
    @Autowired
    private SelectAmazonProductScheduler selectAmazonProductScheduler;

    @PostMapping("/list")
    public Result<List<AmazonCategory>> query(@Valid @RequestBody AmazonCategory vO) {
//        var list = amazonCategoryService.querySubCategoriesLevel2(vO);
        var list = amazonCategoryService.findAllChildCategories(vO);
        return Result.success(list);
    }
    @PostMapping("/searchCategoryName")
    public Result<List<AmazonCategory>> searchCategoryName(@Valid @RequestBody AmazonCategory vO) {
        var list = amazonCategoryService.queryLevel2TreeByCategoryName(vO);

        return Result.success(list);
    }
    @PostMapping("/fetchAsins")
    public Result<List<AmazonCategory>> fetchAsins(@Valid @RequestBody List<AmazonCategory> list) throws DatatypeConfigurationException, ParserConfigurationException, IOException, InterruptedException {
        for(var category:list){
            var startFetch = selectAmazonProductScheduler.selectAmazonProductXxlJobHandler("FetchByCategory,"+category.getMarketplaceId()+","+category.getCategoryId());
            if(!startFetch){
                return Result.failed("有其他任务在执行中，请稍后再试");
            }
        }

        return Result.success(list);
    }
}