package com.yy.stock.service;

import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.repository.StockStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StockStatusService {

    @Autowired
    private StockStatusRepository statusRepository;

    public BigInteger save(StockStatus s) {
        statusRepository.save(s);
        return s.getId();
    }

    public void delete(BigInteger id) {
        statusRepository.deleteById(id);
    }

    public StockStatus getById(BigInteger id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public StockStatus getOrCreateByOrderItemSku(OrderItemAdaptorInfoDTO orderItemInfo) {
        StockStatus stockStatus = getOrCreateByOrderItemSku(orderItemInfo.getAuthid(), orderItemInfo.getMarketplaceId(), orderItemInfo.getOrderid(), orderItemInfo.getSku());
        if (stockStatus.getOrderItemId() == null) {
            stockStatus.setOrderItemId(orderItemInfo.getOrderItemId());
            stockStatus.setAmazonPurchaseTime(orderItemInfo.getBuydate());
            statusRepository.save(stockStatus);
        }
        return stockStatus;
    }


    public List<StockStatus> getOnTheWayOrders() {
        var statuses = new ArrayList<String>();
//        statuses.add(StatusEnum.stockedUnshipped.name());
        statuses.add(StatusEnum.shipped.name());
        statuses.add(StatusEnum.shippedAskReturn.name());
        statuses.add(StatusEnum.payedButInfoSaveError.name());
        statuses.add(StatusEnum.stockedUnshippedAskReturn.name());
        statuses.add(StatusEnum.deliveredAskReturn.name());
        statuses.add(StatusEnum.shippedLosed.name());
        var trackTime = LocalDateTime.now().minusHours(12);
        var oldStockList = statusRepository.findAllByStatusInAndLastShipmentTrackTimeBefore(statuses, trackTime);
        var virgins = statusRepository.findAllByStatusInAndLastShipmentTrackTimeIsNull(statuses);
        virgins.addAll(oldStockList);
        return virgins;
    }

    public List<StockStatus> getStockedUnshippedOrders() {
        return statusRepository.findAllByStatusIs(StatusEnum.stockedUnshipped.name());
    }


    public StockStatus getOrCreateByOrderItemSku(BigInteger authId, String marketpalceId, String orderId, String sku) {
        StockStatus stockStatus = statusRepository.findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndAmazonSku(authId, marketpalceId, orderId, sku);
        if (stockStatus == null) {
            StockStatus toCreate = new StockStatus();
            toCreate.setMarketplaceId(marketpalceId);
            toCreate.setAmazonOrderId(orderId);
            toCreate.setAmazonAuthId(authId);
            toCreate.setAmazonSku(sku);
            toCreate.setStockful(true);
            toCreate.setStatus(StatusEnum.unstocked.name());
            statusRepository.save(toCreate);

            stockStatus = toCreate;
        }
        return stockStatus;
    }

    public void update(StockStatus s) {
        statusRepository.save(s);
    }

}
