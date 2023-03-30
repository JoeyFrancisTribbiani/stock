package com.yy.stock.controller;

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

import java.io.File;
import java.io.IOException;

@Validated
@Slf4j
@RestController
@RequestMapping("/emailAccount")
public class EmailAccountController {

    @Autowired
    private EmailAccountService emailAccountService;

    @PostMapping
    public String save(@Valid @RequestBody EmailAccount vO) {
        return emailAccountService.save(vO).toString();
    }

    //表单提交必须用post请求
    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg") MultipartFile headerImg, // MultipartFile：用于上传文件功能   @RequestPart：获取表单里的文件项
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {

        log.info("上传的信息：email={},username={},headerImg={},photos={}",
                email, username, headerImg.getSize(), photos.length);

        if (!headerImg.isEmpty()) { //isEmpty，getOriginalFilename，transferTo：都是MultipartFile接口里的方法
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("D:\\cache\\" + originalFilename));//transferTo：文件保存（传输）到哪
        }
        if (photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("D:\\cache\\" + originalFilename));
                }
            }
        }
        return "main";
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

    @GetMapping
    public Page<EmailAccount> query(@Valid EmailAccountListQuery vO) {
        return emailAccountService.query(vO);
    }
}
