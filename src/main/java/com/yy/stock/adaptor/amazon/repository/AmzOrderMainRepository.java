package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.dto.AmzOrderMainDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrderMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author minmin
 */
public interface AmzOrderMainRepository extends JpaRepository<AmzOrderMain, String>, JpaSpecificationExecutor<AmzOrderMain> {
    public List<AmzOrderMainDTO> findAmzOrderMainsByOrderStatusIs(String orderStatus);

}