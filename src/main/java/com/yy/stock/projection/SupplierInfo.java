package com.yy.stock.projection;

/**
 * A Projection for the {@link com.yy.stock.entity.Supplier} entity
 */
public interface SupplierInfo {
    Long getId();

    String getMarketplaceId();

    String getAmazonSku();

    String getAmazonName();

    String getAmazonAsin();

    Long getPlatformId();

    String getUrl();

    String getName();

    Integer getStockLimitation();

    Integer getShipLimitation();

    String getShopName();

    Byte getIsAvailable();

    Byte getIsMainland();

    Float getPrice();
}