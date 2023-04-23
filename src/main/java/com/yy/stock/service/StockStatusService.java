package com.yy.stock.service;

import com.yy.stock.adaptor.amazon.api.pojo.vo.AmazonOrdersVo;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockStatusEnum;
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
        statuses.add(StockStatusEnum.shipped.name());
        statuses.add(StockStatusEnum.shippedAskReturn.name());
        statuses.add(StockStatusEnum.payedButInfoSaveError.name());
        statuses.add(StockStatusEnum.stockedUnshippedAskReturn.name());
        statuses.add(StockStatusEnum.deliveredAskReturn.name());
        statuses.add(StockStatusEnum.shippedLosed.name());
        var trackTime = LocalDateTime.now().minusHours(12);
        var oldStockList = statusRepository.findAllByStatusInAndLastShipmentTrackTimeBefore(statuses, trackTime);
        var virgins = statusRepository.findAllByStatusInAndLastShipmentTrackTimeIsNull(statuses);
        virgins.addAll(oldStockList);
        return virgins;
    }

    public List<StockStatus> getStockedUnshippedOrders() {
        return statusRepository.findAllByStatusIs(StockStatusEnum.stockedUnshipped.name());
    }

    public List<StockStatus> getByAmazonOrderList(BigInteger authId, String marketpalceId, List<AmazonOrdersVo> amazonOrderVoList) {
        var orderIdList = amazonOrderVoList.stream().map(AmazonOrdersVo::getOrderid).toList();
        var stockStatusList = statusRepository.findAllByAmazonAuthIdAndMarketplaceIdAndAmazonOrderIdIn(authId, marketpalceId, orderIdList);
        if (stockStatusList.size() == 0) {
            return new ArrayList<>();
        }
        List<StockStatus> notCreated = new ArrayList<>();
        for (AmazonOrdersVo amazonOrderVo : amazonOrderVoList) {
            var orderid = amazonOrderVo.getOrderid();
            var sku = amazonOrderVo.getSku();
            var stockStatus = stockStatusList.stream().filter(s -> s.getAmazonOrderId().equals(orderid) && s.getAmazonSku().equals(sku)).findFirst().orElse(null);
            if (stockStatus == null) {
                StockStatus toCreate = new StockStatus();
                toCreate.setMarketplaceId(marketpalceId);
                toCreate.setAmazonOrderId(orderid);
                toCreate.setAmazonAuthId(authId);
                toCreate.setAmazonSku(sku);
                toCreate.setStockful(true);
                toCreate.setStatus(StockStatusEnum.unstocked.name());
                notCreated.add(toCreate);
            }
        }
        if (notCreated.size() > 0) {
            statusRepository.saveAll(notCreated);
            stockStatusList.addAll(notCreated);
        }
        return stockStatusList;
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
            toCreate.setStatus(StockStatusEnum.unstocked.name());
            statusRepository.save(toCreate);

            stockStatus = toCreate;
        }
        return stockStatus;
    }

    public void update(StockStatus s) {
        statusRepository.save(s);
    }

}
