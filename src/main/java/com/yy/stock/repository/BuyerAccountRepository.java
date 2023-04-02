package com.yy.stock.repository;

import com.yy.stock.entity.BuyerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface BuyerAccountRepository extends JpaRepository<BuyerAccount, BigInteger>, JpaSpecificationExecutor<BuyerAccount> {
    @Query("SELECT e FROM BuyerAccount e WHERE e.platform =:platformId and e.inBuying =false ORDER BY e.orderCount asc LIMIT 1")
    public BuyerAccount findBuyerAccountByLeastOrderCountAndNotBuying(BigInteger platformId);

    @Query("SELECT e FROM BuyerAccount e WHERE e.platform =:platformId ORDER BY e.lastLoginTime desc LIMIT 1")
    public BuyerAccount findBuyerAccountByLatestLoginTime(BigInteger platformId);

    @Query("SELECT e FROM BuyerAccount e WHERE e.platform =:platformId ORDER BY e.lastLoginTime asc LIMIT 1")
    public BuyerAccount findBuyerAccountByEarliestLoginTime(BigInteger platformId);
}