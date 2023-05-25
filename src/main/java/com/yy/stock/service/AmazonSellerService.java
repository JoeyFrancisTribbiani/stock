package com.yy.stock.service;

import com.yy.stock.entity.AmazonSeller;
import com.yy.stock.repository.AmazonSellerRepository;
import com.yy.stock.vo.AmazonSellerQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AmazonSellerService {

    @Autowired
    private AmazonSellerRepository amazonSellerRepository;

    public void save(AmazonSeller vO) {
        amazonSellerRepository.save(vO);
    }

    public void delete(String id) {
        amazonSellerRepository.deleteById(id);
    }

    public void update(String id, AmazonSeller vO) {
        amazonSellerRepository.save(vO);
    }


    public Page<AmazonSeller> query(AmazonSellerQueryVO vO) {
        Specification<AmazonSeller> specification = vO.toSpecification();
        var pageRequest = vO.toPageRequest();
        var queryPage = amazonSellerRepository.findAll(specification, pageRequest);
        return queryPage;
    }

    public AmazonSeller getByMarketplaceIdAndSellerId(String marketplaceId, String sellerId) {
        return amazonSellerRepository.findByMarketplaceIdAndSellerId(marketplaceId, sellerId);
    }
}
