package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockStockStatusServiceTest {
    @Autowired
    private StockStatusService stockStatusService;

    @Test
    void getOrCreate() {
        String mId = "A2Q3Y263D00KWC";
        String oId = "701-2153377-3217026";
        BigInteger aId = new BigInteger("888888");

        OrderItemAdaptorInfoDTO orderItemInfo = new OrderItemAdaptorInfoDTO() {
            @Override
            public Date getBuydate() {
                return null;
            }

            @Override
            public String getItemstatus() {
                return null;
            }

            @Override
            public String getOrderstatus() {
                return null;
            }

            @Override
            public String getOrderid() {
                return oId;
            }

            @Override
            public BigInteger getGroupid() {
                return null;
            }

            @Override
            public String getChannel() {
                return null;
            }

            @Override
            public BigDecimal getOrderprice() {
                return null;
            }

            @Override
            public BigDecimal getItemprice() {
                return null;
            }

            @Override
            public Integer getQuantity() {
                return null;
            }

            @Override
            public String getImage() {
                return null;
            }

            @Override
            public String getSku() {
                return null;
            }

            @Override
            public String getAsin() {
                return null;
            }

            @Override
            public String getGroupname() {
                return null;
            }

            @Override
            public String getColor() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getMarket() {
                return null;
            }

            @Override
            public String getCurrency() {
                return null;
            }

            @Override
            public String getShipservice() {
                return null;
            }

            @Override
            public String getCity() {
                return null;
            }

            @Override
            public BigDecimal getShipfee() {
                return null;
            }

            @Override
            public BigDecimal getShipdiscount() {
                return null;
            }

            @Override
            public BigDecimal getItemdiscount() {
                return null;
            }

            @Override
            public BigInteger getAuthid() {
                return aId;
            }

            @Override
            public String getRemark() {
                return null;
            }

            @Override
            public boolean getIsbusiness() {
                return false;
            }

            @Override
            public String getMarketplaceId() {
                return mId;
            }

            @Override
            public String getMarketname() {
                return null;
            }

            @Override
            public String getRegion() {
                return null;
            }

            @Override
            public String getFeedstatus() {
                return null;
            }

            @Override
            public BigInteger getFeedid() {
                return null;
            }
        };

        StockStatus r = stockStatusService.getOrCreateByOrderItemInfo(orderItemInfo);
        Assert.notNull(r);
        Assert.notNull(r.getId());
    }
}