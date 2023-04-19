package com.yy.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "buyer_account")
public class BuyerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    @JsonIgnoreProperties({"buyerAccounts"})
    private Platform platform;

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
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    @Column(name = "last_pay_time")
    private Date lastPayTime;
    @Column(name = "bot_status")
    @ColumnDefault("'idle'")
    private String botStatus;
    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;
    @Column(name = "credit_card")
    private String creditCard;
}
