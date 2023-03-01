package com.yy.stock.projection;

import java.time.Instant;

/**
 * A Projection for the {@link com.yy.stock.entity.Status} entity
 */
public interface StatusInfo {
    Long getId();

    Long getOrderItemId();

    Long getSupplierId();

    Instant getStockTime();

    Integer getStatus();

    String getShipment();
}