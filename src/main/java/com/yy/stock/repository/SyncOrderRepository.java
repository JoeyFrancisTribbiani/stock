package com.yy.stock.repository;

import com.yy.stock.entity.SyncOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SyncOrderRepository extends JpaRepository<SyncOrder, Long>, JpaSpecificationExecutor<SyncOrder> {
    public List<SyncOrder> findAllBySyncSwitchIs(boolean isOpened);
}