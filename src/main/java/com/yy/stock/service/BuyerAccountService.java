package com.yy.stock.service;

import com.yy.stock.dto.BuyerAccountDTO;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.repository.BuyerAccountRepository;
import com.yy.stock.vo.BuyerAccountQueryVO;
import com.yy.stock.vo.BuyerAccountUpdateVO;
import com.yy.stock.vo.BuyerAccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BuyerAccountService {

    @Autowired
    private BuyerAccountRepository buyerAccountRepository;

    public Long save(BuyerAccountVO vO) {
        BuyerAccount bean = new BuyerAccount();
        BeanUtils.copyProperties(vO, bean);
        bean = buyerAccountRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        buyerAccountRepository.deleteById(id);
    }

    public void update(Long id, BuyerAccountUpdateVO vO) {
        BuyerAccount bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        buyerAccountRepository.save(bean);
    }

    public BuyerAccountDTO getById(Long id) {
        BuyerAccount original = requireOne(id);
        return toDTO(original);
    }

    public BuyerAccountDTO getNewestBuyer() {
        BuyerAccount original = buyerAccountRepository.findBuyerAccountByMinOrderCount();
        return toDTO(original);
    }

    public Page<BuyerAccountDTO> query(BuyerAccountQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    public long count() {
        return buyerAccountRepository.count();
    }

    private BuyerAccountDTO toDTO(BuyerAccount original) {
        BuyerAccountDTO bean = new BuyerAccountDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private BuyerAccount requireOne(Long id) {
        return buyerAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
