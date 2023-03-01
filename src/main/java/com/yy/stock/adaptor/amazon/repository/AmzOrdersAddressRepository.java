package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface AmzOrdersAddressRepository extends JpaRepository<AmzOrdersAddress, String>, JpaSpecificationExecutor<AmzOrdersAddress> {
    public AmzOrdersAddress findByAmazonOrderIdAndAmazonAuthId(@Param("amazonorderid") String amazonOrderId, @Param("amazonauthid") BigInteger amazonAuthId);

}