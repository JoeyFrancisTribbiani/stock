package com.yy.stock.repository;

import com.yy.stock.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface SupplierRepository extends JpaRepository<Supplier, BigInteger>, JpaSpecificationExecutor<Supplier> {
    public Supplier findByMarketplaceIdAndAmazonSku(String marketplaceId, String amazonSku);

}