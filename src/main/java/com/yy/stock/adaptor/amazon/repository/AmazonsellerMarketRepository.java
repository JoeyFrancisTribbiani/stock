package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmazonsellerMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmazonsellerMarketRepository extends JpaRepository<AmazonsellerMarket, String>, JpaSpecificationExecutor<AmazonsellerMarket> {

}