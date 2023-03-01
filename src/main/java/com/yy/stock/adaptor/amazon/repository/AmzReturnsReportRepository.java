package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.AmzReturnsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmzReturnsReportRepository extends JpaRepository<AmzReturnsReport, String>, JpaSpecificationExecutor<AmzReturnsReport> {

}