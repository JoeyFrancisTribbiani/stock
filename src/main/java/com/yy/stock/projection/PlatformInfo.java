package com.yy.stock.projection;

/**
 * A Projection for the {@link com.yy.stock.entity.Platform} entity
 */
public interface PlatformInfo {
    Long getId();

    String getName();

    String getCountry();

    String getLoginUrl();

    String getMsgUrl();
}