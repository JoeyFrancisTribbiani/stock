package com.yy.stock.projection;

import com.yy.stock.entity.StockStatus;

import java.time.Instant;

/**
 * A Projection for the {@link StockStatus} entity
 */
public interface StatusInfo {
    Long getId();

    Long getOrderItemId();

    Long getSupplierId();

    Instant getStockTime();

    Integer getStatus();

    String getShipment();
}