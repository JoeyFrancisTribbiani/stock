package com.yy.stock.repository;

import com.yy.stock.entity.AmazonCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface AmazonCategoryRepository extends JpaRepository<AmazonCategory, BigInteger>, JpaSpecificationExecutor<AmazonCategory> {

}