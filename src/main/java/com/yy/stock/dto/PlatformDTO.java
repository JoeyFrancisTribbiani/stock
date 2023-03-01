package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class PlatformDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private String country;

    private String loginUrl;

    private String msgUrl;

}
