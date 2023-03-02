package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * stock record from supplier
 */
@Data
@Entity
@Table(name = "buyer_account")
public class BuyerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_id", nullable = false)
    private Long platformId;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email2")
    private String email2;

    @Column(name = "login_cookie")
    private String loginCookie;
    @Column(name = "order_count")
    private int orderCount;

    @Column(name = "verify_email")
    private String verifyEmail;

    @Column(name = "verify_email_password")
    private String verifyEmailPassword;
}
