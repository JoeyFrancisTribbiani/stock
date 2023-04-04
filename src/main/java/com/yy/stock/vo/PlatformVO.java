package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;

    @NotNull(message = "name can not null")
    private String name;

    @NotNull(message = "country can not null")
    private String country;

    private String botBean;

    @NotNull(message = "loginUrl can not null")
    private String loginUrl;

    private String msgUrl;

}
