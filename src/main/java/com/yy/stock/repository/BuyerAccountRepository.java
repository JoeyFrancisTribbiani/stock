package com.yy.stock.repository;

import com.yy.stock.entity.BuyerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface BuyerAccountRepository extends JpaRepository<BuyerAccount, BigInteger>, JpaSpecificationExecutor<BuyerAccount> {
    @Query("SELECT e FROM BuyerAccount e WHERE e.platform.id =:platformId and e.botStatus ='idle' and e.status='active' and e.role='stocker' ORDER BY e.orderCount asc LIMIT 1")
    public BuyerAccount findBuyerAccountByLeastOrderCountAndIdle(BigInteger platformId);

    //
    @Query("SELECT e FROM BuyerAccount e WHERE e.platform.id =:platformId and  e.botStatus='idle' and e.status='active'  and e.role='stocker'  ORDER BY e.lastLoginTime desc LIMIT 1")
    public BuyerAccount findBuyerAccountByLatestLoginTimeAndIdle(BigInteger platformId);

    //
    @Query("SELECT e FROM BuyerAccount e WHERE e.platform.id = :platformId and e.botStatus='idle' and e.status='active'  and e.role='stocker'  ORDER BY e.lastLoginTime asc LIMIT 1")
    public BuyerAccount findBuyerAccountByEarliestLoginTimeAndIdle(BigInteger platformId);

    @Query("SELECT e FROM BuyerAccount e WHERE e.platform.id = :platformId   and e.status='active' and e.role='fetcher' ORDER BY e.lastLoginTime asc LIMIT 1")
    public BuyerAccount findFetcherBuyerAccount(BigInteger platformId);
}