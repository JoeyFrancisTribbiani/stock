package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>, JpaSpecificationExecutor<ProductInfo> {
    ProductInfo findByAmazonAuthIdAndMarketplaceidAndAsin(String amazonAuthId, String marketplaceid, String asin);

}