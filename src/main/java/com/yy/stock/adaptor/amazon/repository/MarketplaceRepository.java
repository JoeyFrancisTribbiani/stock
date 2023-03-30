package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmazonAuth;
import com.yy.stock.adaptor.amazon.entity.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketplaceRepository extends JpaRepository<Marketplace, String>, JpaSpecificationExecutor<AmazonAuth> {

}