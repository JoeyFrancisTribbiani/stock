package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class BuyerAccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long platformId;

    private String email;

    private String mobile;

    private String password;

    private String username;

    private String nickname;

    private String email2;

    private String loginCookie;
    private int orderCount;

}
