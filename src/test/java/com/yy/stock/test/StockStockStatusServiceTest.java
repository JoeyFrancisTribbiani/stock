package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.stock.adaptor.amazon.api.AmazonClientOneFeign;
import com.yy.stock.adaptor.amazon.api.pojo.dto.ProductListQuery;
import com.yy.stock.adaptor.amazon.api.pojo.vo.AmzProductListVo;
import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;
import com.yy.stock.common.result.Result;
import com.yy.stock.controller.SupplierController;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockStockStatusServiceTest {
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;
    @Autowired
    private SupplierController supplierController;

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

            /**
             * @return
             */
            @Override
            public String getOrderItemId() {
                return null;
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

        StockStatus r = stockStatusService.getOrCreateByOrderItemSku(orderItemInfo);
        Assert.notNull(r);
        Assert.notNull(r.getId());
    }

    @Test
    void testStatusList() throws IOException {
        var queryStr = "{\"currentpage\":1,\"pagesize\":20,\"sort\":\"sku\",\"order\":\"asc\",\"paramother\":{},\"search\":null,\"searchtype\":\"sku\",\"marketplace\":\"A2Q3Y263D00KWC\",\"disable\":\"false\",\"changeRate\":null,\"color\":null,\"isfba\":\"\",\"groupid\":\"100189443700293633\",\"ownerid\":null,\"isparent\":null,\"parentasin\":null,\"name\":null,\"remark\":null,\"category\":null,\"isbadreview\":null,\"salestatus\":null,\"sku\":null,\"paralist\":null,\"taglist\":null,\"page\":{\"records\":[],\"total\":0,\"size\":20,\"current\":1,\"orders\":[{\"column\":\"sku\",\"asc\":true}],\"optimizeCountSql\":true,\"searchCount\":true,\"maxLimit\":null,\"countId\":null,\"pages\":0}}";
        var query = new ObjectMapper().readValue(queryStr, ProductListQuery.class);
        Result<Page<AmzProductListVo>> result = amazonClientOneFeign.getProductListAction(query);

        if (Result.isSuccess(result) && result.getData() != null) {
            var stockResult = new Result<Page<StockStatusListVo>>();
            BeanUtils.copyProperties(result, stockResult);
            for (var statusVo : stockResult.getData().getRecords()) {
                var authId = statusVo.getAmazonAuthId();
                var sku = statusVo.getSku();
                var marketplaceId = statusVo.getMarketplaceid();
            }
        }
    }

    @Test
    void testGetProductHtml() throws IOException {
//        var html = supplierController.getSupplierHtmlSource("https://www.aliexpress.com/item/3256804973961145.html");
//        var html = supplierController.getSupplierHtmlSource("https://www.aliexpress.com/item/2251832067229696.html");
//        System.out.println(html);
    }
}