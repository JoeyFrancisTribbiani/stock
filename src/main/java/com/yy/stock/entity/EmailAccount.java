package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "email_account")
public class EmailAccount {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

}