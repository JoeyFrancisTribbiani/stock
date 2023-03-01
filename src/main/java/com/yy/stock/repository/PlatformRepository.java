package com.yy.stock.repository;

import com.yy.stock.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlatformRepository extends JpaRepository<Platform, Long>, JpaSpecificationExecutor<Platform> {

}