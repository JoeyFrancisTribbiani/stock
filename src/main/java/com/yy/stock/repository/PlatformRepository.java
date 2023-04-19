package com.yy.stock.repository;

import com.yy.stock.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface PlatformRepository extends JpaRepository<Platform, BigInteger>, JpaSpecificationExecutor<Platform> {
    public Platform findFirstByCodeAndCountryCode(String name, String country);

}