package com.yy.stock.adaptor.amazon.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ErpShipStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;

    private String content;

    private String name;

}
