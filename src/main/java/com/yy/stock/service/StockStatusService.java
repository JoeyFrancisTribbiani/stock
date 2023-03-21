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

    public StockStatus getOrCreateByOrderItemInfo(OrderItemAdaptorInfoDTO orderItemInfo) {
        StockStatus stockStatus = statusRepository.findFirstBymarketplaceIdAndAmazonAuthIdAndAmazonOrderId(orderItemInfo.getMarketplaceId(), orderItemInfo.getAuthid(), orderItemInfo.getOrderid());
        if (stockStatus == null) {
            StockStatus toCreate = new StockStatus();
            toCreate.setMarketplaceId(orderItemInfo.getMarketplaceId());
            toCreate.setAmazonOrderId(orderItemInfo.getOrderid());
            toCreate.setAmazonAuthId(orderItemInfo.getAuthid());
            toCreate.setStatus(StatusEnum.unstocked.name());
            statusRepository.save(toCreate);

            stockStatus = toCreate;
        }
        return stockStatus;
    }

    public void update(StockStatus s) {
        statusRepository.save(s);
    }

    public boolean saveStatus(StockStatus stockStatus, StatusEnum status) {
        try {
            stockStatus.setStatus(status.name());
            statusRepository.save(stockStatus);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
