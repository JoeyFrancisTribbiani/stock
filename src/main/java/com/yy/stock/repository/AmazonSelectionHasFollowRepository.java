package com.yy.stock.repository;

import com.yy.stock.entity.AmazonSelectionHasFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface AmazonSelectionHasFollowRepository extends JpaRepository<AmazonSelectionHasFollow, BigInteger>, JpaSpecificationExecutor<AmazonSelectionHasFollow> {

    List<AmazonSelectionHasFollow> findAllByFollowSellSwitchIsFalseAndHasFollowSellIsTrue();
    List<AmazonSelectionHasFollow> findAllByAmazonAuthIdAndAmazonSelectionIdIn(BigInteger amazonAuthId, List<BigInteger> selectionId);
    AmazonSelectionHasFollow findByAmazonAuthIdAndAmazonSelectionId(BigInteger amazonAuthId, BigInteger selectionId);
}