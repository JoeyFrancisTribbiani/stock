package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.entity.Marketplace;
import com.yy.stock.adaptor.amazon.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MarketplaceService {

    @Autowired
    private MarketplaceRepository marketplaceRepository;


    public Marketplace getById(String id) {
        return requireOne(id);
    }

    private Marketplace requireOne(String id) {
        return marketplaceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
