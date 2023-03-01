package com.yy.stock.adaptor.amazon.repository;

import com.yy.stock.adaptor.amazon.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopRepository extends JpaRepository<Shop, String>, JpaSpecificationExecutor<Shop> {

}