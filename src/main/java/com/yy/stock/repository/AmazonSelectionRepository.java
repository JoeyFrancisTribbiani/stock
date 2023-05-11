package com.yy.stock.repository;

import com.yy.stock.entity.AmazonSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.List;

public interface AmazonSelectionRepository extends JpaRepository<AmazonSelection, BigInteger>, JpaSpecificationExecutor<AmazonSelection> {
    List<AmazonSelection> findAllByMarketplaceIdAndAsin(String marketplaceId, String asin);
    AmazonSelection findByMarketplaceIdAndAsin(String marketplaceId, String asin);
}