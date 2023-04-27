package com.yy.stock.repository;

import com.yy.stock.entity.AmazonSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmazonSelectionRepository extends JpaRepository<AmazonSelection, String>, JpaSpecificationExecutor<AmazonSelection> {

}