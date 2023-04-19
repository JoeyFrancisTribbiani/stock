package com.yy.stock.vo;


import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerAccountQueryVO extends BasePageQuery {
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

}
