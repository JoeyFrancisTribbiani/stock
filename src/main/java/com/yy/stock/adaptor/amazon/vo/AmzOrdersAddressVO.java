package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class AmzOrdersAddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "amazonOrderId can not null")
    private String amazonOrderId;

    private String amazonAuthId;

    private String marketplaceId;

    private String name;

    private String phone;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String addressType;

    private String city;

    private String countryCode;

    private String county;

    private String district;

    private String municipality;

    private String postalCode;

    private String stateOrRegion;

    private Date opttime;

}
