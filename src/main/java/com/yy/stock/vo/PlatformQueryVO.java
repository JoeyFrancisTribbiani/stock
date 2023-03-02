package com.yy.stock.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class PlatformQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String country;

    private String botBean;

    private String loginUrl;

    private String msgUrl;

}
