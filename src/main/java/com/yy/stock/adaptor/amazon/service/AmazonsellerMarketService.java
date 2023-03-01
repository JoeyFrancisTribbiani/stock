package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmazonsellerMarketDTO;
import com.yy.stock.adaptor.amazon.entity.AmazonsellerMarket;
import com.yy.stock.adaptor.amazon.repository.AmazonsellerMarketRepository;
import com.yy.stock.adaptor.amazon.vo.AmazonsellerMarketQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmazonsellerMarketUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AmazonsellerMarketService {

    @Autowired
    private AmazonsellerMarketRepository amazonsellerMarketRepository;


    public void delete(String id) {
        amazonsellerMarketRepository.deleteById(id);
    }

    public void update(String id, AmazonsellerMarketUpdateVO vO) {
        AmazonsellerMarket bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amazonsellerMarketRepository.save(bean);
    }

    public AmazonsellerMarketDTO getById(String id) {
        AmazonsellerMarket original = requireOne(id);
        return toDTO(original);
    }

    public Page<AmazonsellerMarketDTO> query(AmazonsellerMarketQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmazonsellerMarketDTO toDTO(AmazonsellerMarket original) {
        AmazonsellerMarketDTO bean = new AmazonsellerMarketDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmazonsellerMarket requireOne(String id) {
        return amazonsellerMarketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
