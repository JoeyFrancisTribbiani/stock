package com.yy.stock.service;

import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.entity.AmazonSelectionHasFollow;
import com.yy.stock.repository.AmazonSelectionHasFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AmazonSelectionHasFollowService {

    @Autowired
    private AmazonSelectionHasFollowRepository amazonSelectionHasFollowRepository;
    @Autowired
    private AmazonSelectionService amazonSelectionService;

    public void save(AmazonSelectionHasFollow vO) {
        amazonSelectionHasFollowRepository.save(vO);
    }

    public AmazonSelectionHasFollow getById(BigInteger id) {
        return amazonSelectionHasFollowRepository.findById(id).orElse(null);
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
    public List<AmazonSelectionHasFollow> getMarketplaceHasFollowed(String marketplaceId, BigInteger authId) {
        var authAll= amazonSelectionHasFollowRepository.findAllByAmazonAuthIdAndHasFollowSellTrue(authId);
        var selectionIds = authAll.stream().map(AmazonSelectionHasFollow::getAmazonSelectionId).toList();
        List<AmazonSelection> selections = amazonSelectionService.getByMarketplaceAndSelectionIds(marketplaceId, selectionIds);
        var selectionsInMarketplcae = selections.stream().filter(s -> s.getMarketplaceId().equals(marketplaceId)).toList();
        var selectionIdsInMarketplace = selectionsInMarketplcae.stream().map(AmazonSelection::getId).toList();
        var authInMarketplace = authAll.stream().filter(a -> selectionIdsInMarketplace.contains(a.getAmazonSelectionId())).toList();
        return authInMarketplace;
    }

    public List<AmazonSelectionHasFollow> getAllHasFollowed(BigInteger authId) {
        var authAll= amazonSelectionHasFollowRepository.findAllByAmazonAuthIdAndHasFollowSellTrue(authId);
        return authAll;
    }

    public List<AmazonSelectionHasFollow> getBySelectionIds(BigInteger amazonAuthId, List<BigInteger> selectionIds) {
        return amazonSelectionHasFollowRepository.findAllByAmazonAuthIdAndAmazonSelectionIdIn(amazonAuthId, selectionIds);
    }
}
