package com.yy.stock.service;

import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.repository.StockStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class StockStatusService {

    @Autowired
    private StockStatusRepository statusRepository;

    public BigInteger save(StockStatus s) {
        statusRepository.save(s);
        return s.getId();
    }

    public void delete(String id) {
        statusRepository.deleteById(id);
    }

    public StockStatus getOrCreateByOrderItemId(OrderItemAdaptorInfoDTO orderItemInfo) {
        StockStatus stockStatus = statusRepository.findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndOrderItemId(orderItemInfo.getAuthid(), orderItemInfo.getMarketplaceId(), orderItemInfo.getOrderid(), orderItemInfo.getOrderItemId());
        if (stockStatus == null) {
            StockStatus toCreate = new StockStatus();
            toCreate.setMarketplaceId(orderItemInfo.getMarketplaceId());
            toCreate.setAmazonOrderId(orderItemInfo.getOrderid());
            toCreate.setAmazonAuthId(orderItemInfo.getAuthid());
            toCreate.setOrderItemId(orderItemInfo.getOrderItemId());
            toCreate.setStatus(StatusEnum.unstocked.name());
            statusRepository.save(toCreate);

            stockStatus = toCreate;
        }
        return stockStatus;
    }


    public StockStatus getOrCreateByOrderItemSku(BigInteger authId, String marketpalceId, String orderId, String sku) {
        StockStatus stockStatus = statusRepository.findFirstByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdAndAmazonSku(authId, marketpalceId, orderId, sku);
        if (stockStatus == null) {
            StockStatus toCreate = new StockStatus();
            toCreate.setMarketplaceId(marketpalceId);
            toCreate.setAmazonOrderId(orderId);
            toCreate.setAmazonAuthId(authId);
            toCreate.setOrderItemId(sku);
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
