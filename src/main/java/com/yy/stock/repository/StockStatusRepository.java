package com.yy.stock.repository;

import com.yy.stock.entity.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface StockStatusRepository extends JpaRepository<StockStatus, String>, JpaSpecificationExecutor<StockStatus> {
    public StockStatus findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndOrderItemId(BigInteger authId, String marketplaceId, String amazonOrderId, String orderItemId);

    public StockStatus findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndAmazonSku(BigInteger authId, String marketplaceId, String amazonOrderId, String sku);

}