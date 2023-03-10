package com.yy.stock.repository;

import com.yy.stock.entity.BuyerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface BuyerAccountRepository extends JpaRepository<BuyerAccount, BigInteger>, JpaSpecificationExecutor<BuyerAccount> {
    @Query("SELECT e FROM BuyerAccount e WHERE e.orderCount = (SELECT MIN(e.orderCount) FROM BuyerAccount e)")
    public BuyerAccount findBuyerAccountByMinOrderCount();

}