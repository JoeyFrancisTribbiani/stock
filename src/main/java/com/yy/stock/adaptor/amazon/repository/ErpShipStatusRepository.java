package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.ErpShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ErpShipStatusRepository extends JpaRepository<ErpShipStatus, String>, JpaSpecificationExecutor<ErpShipStatus> {

}