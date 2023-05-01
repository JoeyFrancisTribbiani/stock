package com.yy.stock.service;

import com.yy.stock.entity.AmazonCategory;
import com.yy.stock.repository.AmazonCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class AmazonCategoryService {

    @Autowired
    private AmazonCategoryRepository amazonCategoryRepository;

    public void save(AmazonCategory vO) {
        amazonCategoryRepository.save(vO);
    }

    public void delete(BigInteger id) {
        amazonCategoryRepository.deleteById(id);
    }
}
