package com.yy.stock.repository;

import com.yy.stock.entity.AmazonSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmazonSellerRepository extends JpaRepository<AmazonSeller, String>, JpaSpecificationExecutor<AmazonSeller> {
    AmazonSeller findByMarketplaceIdAndSellerId(String marketplaceId, String sellerId);

}