package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface AmzOrdersAddressRepository extends JpaRepository<AmzOrdersAddress, String>, JpaSpecificationExecutor<AmzOrdersAddress> {
    public AmzOrdersAddress findByMarketplaceIdAndAmazonAuthIdAndAmazonOrderId(@Param("marketplaceid") String marketplaceId, @Param("amazonauthid") BigInteger amazonAuthId, @Param("amazonorderid") String amazonOrderId);

    public List<AmzOrdersAddress> findAllByMarketplaceIdAndAmazonAuthIdAndAmazonOrderIdIn(@Param("marketplaceid") String marketplaceId, @Param("amazonauthid") BigInteger amazonAuthId, @Param("amazonorderid") List<String> amazonOrderIdList);

}