package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrdersReportRepository extends JpaRepository<OrdersReport, String>, JpaSpecificationExecutor<OrdersReport> {
    public List<OrdersReportDTO> findAmzOrderMainsByOrderStatusIs(String orderStatus);

}