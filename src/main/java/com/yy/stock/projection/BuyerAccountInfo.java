package com.yy.stock.projection;

/**
 * A Projection for the {@link com.yy.stock.entity.BuyerAccount} entity
 */
public interface BuyerAccountInfo {
    Long getId();

    Long getPlatformId();

    String getEmail();

    String getMobile();

    String getPassword();

    String getUsername();

    String getNickname();

    String getEmail2();

    String getLoginCookie();
}