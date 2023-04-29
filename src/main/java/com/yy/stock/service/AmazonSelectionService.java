package com.yy.stock.service;

import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.repository.AmazonSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AmazonSelectionService {

    @Autowired
    private AmazonSelectionRepository amazonSelectionRepository;

    public Page<AmazonSelection> query(AmazonSelection vO) {
        throw new UnsupportedOperationException();
    }

    public void save(AmazonSelection selection) {
        amazonSelectionRepository.save(selection);
    }

    public Optional<AmazonSelection> getOneByAsin(String asin) {
        return amazonSelectionRepository.findById(asin);
    }
}
