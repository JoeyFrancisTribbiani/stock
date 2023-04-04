package com.yy.stock.adaptor.amazon.vo;


import java.io.Serializable;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class ErpShipStatusQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;

    private String content;

    private String name;

}
