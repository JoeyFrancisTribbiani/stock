package com.yy.stock.repository;

import com.yy.stock.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, BigInteger>, JpaSpecificationExecutor<Supplier> {
    public Supplier findByAmazonAuthIdAndMarketplaceIdAndAmazonSku(BigInteger amazonAuthId, String marketplaceId, String amazonSku);

    public List<Supplier> findByAmazonAuthIdAndMarketplaceIdAndAmazonSkuIn(BigInteger amazonAuthId, String marketplaceId, List<String> amazonSkuList);

}