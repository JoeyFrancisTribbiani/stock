package com.yy.stock.repository;

import com.yy.stock.entity.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemStatusRepository extends JpaRepository<OrderItemStatus, String>, JpaSpecificationExecutor<OrderItemStatus> {

}