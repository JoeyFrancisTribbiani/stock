package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.OrdersFulfilledShipmentsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersFulfilledShipmentsReportRepository extends JpaRepository<OrdersFulfilledShipmentsReport, String>, JpaSpecificationExecutor<OrdersFulfilledShipmentsReport> {

}