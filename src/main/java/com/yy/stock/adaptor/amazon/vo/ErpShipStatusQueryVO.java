package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class ErpShipStatusQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;

    private String content;

    private String name;

}
