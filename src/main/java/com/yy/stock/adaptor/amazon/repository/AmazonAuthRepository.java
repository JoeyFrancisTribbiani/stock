package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmazonAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmazonAuthRepository extends JpaRepository<AmazonAuth, String>, JpaSpecificationExecutor<AmazonAuth> {

}