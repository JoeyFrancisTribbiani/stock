package com.yy.stock.repository;

import com.yy.stock.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatusRepository extends JpaRepository<Status, String>, JpaSpecificationExecutor<Status> {

}