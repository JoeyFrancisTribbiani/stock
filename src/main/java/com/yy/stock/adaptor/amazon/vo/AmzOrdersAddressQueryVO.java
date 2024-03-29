package com.yy.stock.adaptor.amazon.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AmzOrdersAddressQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
