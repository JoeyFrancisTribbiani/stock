package com.yy.stock.repository;

import com.yy.stock.entity.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StockStatusRepository extends JpaRepository<StockStatus, BigInteger>, JpaSpecificationExecutor<StockStatus> {
//    public StockStatus findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndOrderItemId(BigInteger authId, String marketplaceId, String amazonOrderId, String orderItemId);

    List<StockStatus> findAllByStatusInAndLastShipmentTrackTimeBefore(Collection<String> statuses, LocalDateTime trackTime);

    List<StockStatus> findAllByStatusInAndLastShipmentTrackTimeIsNull(Collection<String> statuses);

    List<StockStatus> findAllByStatusIs(String status);

    StockStatus findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndAmazonSku(BigInteger authId, String marketplaceId, String amazonOrderId, String sku);
}