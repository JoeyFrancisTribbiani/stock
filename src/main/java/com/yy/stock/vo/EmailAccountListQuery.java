package com.yy.stock.vo;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
public class EmailAccountListQuery extends PageRequest {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "login_url")
    private String loginUrl;

    @Column(name = "login_cookie")
    private String loginCookie;

    @Column(name = "verify_email")
    private String verifyEmail;

    @Column(name = "verify_email_password")
    private String verifyEmailPassword;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "status")
    private String status;

    @Column(name = "mobile")
    private String mobile;

    protected EmailAccountListQuery(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
