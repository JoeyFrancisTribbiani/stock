package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmzOrderItemRepository extends JpaRepository<AmzOrderItem, String>, JpaSpecificationExecutor<AmzOrderItem> {

}