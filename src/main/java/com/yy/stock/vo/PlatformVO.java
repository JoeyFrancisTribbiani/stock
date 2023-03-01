package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class PlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;

    @NotNull(message = "name can not null")
    private String name;

    @NotNull(message = "country can not null")
    private String country;

    @NotNull(message = "loginUrl can not null")
    private String loginUrl;

    private String msgUrl;

}
