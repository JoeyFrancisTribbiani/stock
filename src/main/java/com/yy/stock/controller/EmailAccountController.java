package com.yy.stock.controller;

import com.yy.stock.common.result.Result;
import com.yy.stock.entity.EmailAccount;
import com.yy.stock.service.EmailAccountService;
import com.yy.stock.vo.EmailAccountListQuery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/emailAccount")
public class EmailAccountController {

    @Autowired
    private EmailAccountService emailAccountService;

    @PostMapping
    public String save(@Valid @RequestBody EmailAccount vO) {
        return emailAccountService.save(vO).toString();
    }

    //表单提交必须用post请求
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile csvFile) throws IOException {
        log.info("上传文件名：{}", csvFile.getOriginalFilename());
        log.info("上传文件大小：{}", csvFile.getSize());
        //按行读取file
        emailAccountService.readCsv(csvFile);

        return Result.success("yeah!");
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        emailAccountService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody EmailAccount vO) {
        emailAccountService.update(id, vO);
    }

    @GetMapping("/{id}")
    public EmailAccount getById(@Valid @NotNull @PathVariable("id") Long id) {
        return emailAccountService.getById(id);
    }

    @PostMapping("/list")
    public Result<Page<EmailAccount>> query(@Valid EmailAccountListQuery vO) {
        var pageble = emailAccountService.query(vO);
        return Result.success(pageble);
    }
}
