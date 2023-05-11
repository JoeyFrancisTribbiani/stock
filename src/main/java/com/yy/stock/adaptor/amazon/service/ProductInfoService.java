package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.entity.ProductInfo;
import com.yy.stock.adaptor.amazon.repository.ProductInfoRepository;
import com.yy.stock.entity.AmazonSelectionHasFollow;
import com.yy.stock.service.AmazonSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private AmazonSelectionService amazonSelectionService;

    public ProductInfo getBySelection(AmazonSelectionHasFollow selectionHasFollow){
        var selection = amazonSelectionService.getById(selectionHasFollow.getAmazonSelectionId());
        var authId = selectionHasFollow.getAmazonAuthId();
        return productInfoRepository.findByAmazonAuthIdAndMarketplaceidAndAsin(authId.toString(), selection.getMarketplaceId(), selection.getAsin());
    }
}
