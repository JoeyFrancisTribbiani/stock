package com.yy.stock.service;

import com.yy.stock.entity.AmazonSelection;
import com.yy.stock.repository.AmazonSelectionRepository;
import com.yy.stock.vo.AmazonSelectionQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AmazonSelectionService {

    @Autowired
    private AmazonSelectionRepository amazonSelectionRepository;

    public Page<AmazonSelection> query(AmazonSelectionQueryVO vO) {
        Specification<AmazonSelection> specification = vO.toSpecification();
        var pageRequest = vO.toPageRequest();
        var queryPage = amazonSelectionRepository.findAll(specification, pageRequest);
        return queryPage;
    }

    public void save(AmazonSelection selection) {
        amazonSelectionRepository.save(selection);
    }

    public AmazonSelection getOneByMarketplaceIdAndAsin(String marketplaceId, String asin) {
        return amazonSelectionRepository.findByMarketplaceIdAndAsin(marketplaceId, asin);
    }


    public List<AmazonSelection> getSameAsin(AmazonSelection vO) {
        return amazonSelectionRepository.findAllByMarketplaceIdAndAsin(vO.getMarketplaceId(), vO.getAsin());
    }

    public void saveBatch(List<AmazonSelection> list) {
        amazonSelectionRepository.saveAll(list);
    }

    public AmazonSelection getById(BigInteger selectionId) {
        return amazonSelectionRepository.getReferenceById(selectionId);
    }
}
