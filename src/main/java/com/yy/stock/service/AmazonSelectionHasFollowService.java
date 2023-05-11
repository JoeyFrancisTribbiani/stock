package com.yy.stock.service;

import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.entity.AmazonSelectionHasFollow;
import com.yy.stock.repository.AmazonSelectionHasFollowRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AmazonSelectionHasFollowService {

    @Autowired
    private AmazonSelectionHasFollowRepository amazonSelectionHasFollowRepository;

    public void save(AmazonSelectionHasFollow vO) {
        amazonSelectionHasFollowRepository.save(vO);
    }
    public AmazonSelectionHasFollow getBySelectionId(BigInteger amazonAuthId, BigInteger selectionId) {
        return amazonSelectionHasFollowRepository.findByAmazonAuthIdAndAmazonSelectionId(amazonAuthId, selectionId);
    }
//    public AmazonSelectionHasFollow getByMarketplaceAndAsin(String marketplaceId, String asin) {
//        return amazonSelectionHasFollowRepository.findByMarketplaceIdAndAsin(marketplaceId, asin);
//    }
    public List<AmazonSelectionHasFollow> getToBeCancelled() {
        return amazonSelectionHasFollowRepository.findAllByFollowSellSwitchIsFalseAndHasFollowSellIsTrue();
    }

    public List<AmazonSelectionHasFollow> getBySelectionIds(BigInteger amazonAuthId, List<BigInteger> selectionIds) {
        return amazonSelectionHasFollowRepository.findAllByAmazonAuthIdAndAmazonSelectionIdIn(amazonAuthId, selectionIds);
    }
}
