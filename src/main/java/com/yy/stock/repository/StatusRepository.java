package com.yy.stock.repository;

import com.yy.stock.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface StatusRepository extends JpaRepository<Status, String>, JpaSpecificationExecutor<Status> {
    public Status findFirstBymarketplaceIdAndAmazonAuthIdAndAmazonOrderId(String marketplaceId, BigInteger authId, String amazonOrderId);
}