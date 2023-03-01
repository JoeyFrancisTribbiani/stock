package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class ErpShipStatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "status can not null")
    private String status;

    @NotNull(message = "content can not null")
    private String content;

    private String name;

}
