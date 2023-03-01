package com.yy.stock.adaptor.amazon.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ErpShipStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;

    private String content;

    private String name;

}
