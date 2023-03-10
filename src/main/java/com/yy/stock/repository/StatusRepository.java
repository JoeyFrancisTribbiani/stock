package com.yy.stock.repository;

import com.yy.stock.entity.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface StatusRepository extends JpaRepository<StockStatus, String>, JpaSpecificationExecutor<StockStatus> {
    public StockStatus findFirstBymarketplaceIdAndAmazonAuthIdAndAmazonOrderId(String marketplaceId, BigInteger authId, String amazonOrderId);
}