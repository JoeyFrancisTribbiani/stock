package com.yy.stock.service;

import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.repository.BuyerAccountRepository;
import com.yy.stock.vo.BuyerAccountQueryVO;
import com.yy.stock.vo.BuyerAccountUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
public class BuyerAccountService {

    @Autowired
    private BuyerAccountRepository buyerAccountRepository;

    public BigInteger save(BuyerAccount b) {
        buyerAccountRepository.save(b);
        return b.getId();
    }

    public void delete(BigInteger id) {
        buyerAccountRepository.deleteById(id);
    }

    public void update(BigInteger id, BuyerAccountUpdateVO vO) {
        BuyerAccount bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        buyerAccountRepository.save(bean);
    }

    public BuyerAccount getById(BigInteger id) {
        BuyerAccount original = requireOne(id);
        return original;
    }

    public BuyerAccount getNewestBuyer() {
        BuyerAccount original = buyerAccountRepository.findBuyerAccountByMinOrderCount();
        return original;
    }

    public Long count() {
        return buyerAccountRepository.count();
    }

    private BuyerAccount requireOne(BigInteger id) {
        return buyerAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public Page<BuyerAccount> query(BuyerAccountQueryVO vO) {
        return null;
    }
}
