package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzOrderBuyerShipAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmzOrderBuyerShipAddressRepository extends JpaRepository<AmzOrderBuyerShipAddress, String>, JpaSpecificationExecutor<AmzOrderBuyerShipAddress> {

}