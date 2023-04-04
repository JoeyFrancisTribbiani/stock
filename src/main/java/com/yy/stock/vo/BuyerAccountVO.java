package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BuyerAccountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;

    @NotNull(message = "platformId can not null")
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
