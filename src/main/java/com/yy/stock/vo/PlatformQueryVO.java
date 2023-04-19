package com.yy.stock.vo;


import com.yy.stock.adaptor.amazon.api.pojo.vo.BasePageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformQueryVO extends BasePageQuery {
    private Long id;

    private String name;

    private String country;

    private String botBean;

    private String loginUrl;

    private String msgUrl;

}
