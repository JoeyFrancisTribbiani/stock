package com.yy.stock.service;

import com.yy.stock.entity.AmazonCategory;
import com.yy.stock.repository.AmazonCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AmazonCategoryService {

    @Autowired
    private AmazonCategoryRepository amazonCategoryRepository;

    public void save(AmazonCategory vO) {
        amazonCategoryRepository.save(vO);
    }

    public AmazonCategory getByMarketplaceIdAndCategoryId(String marketplaceId, String id) {
        return amazonCategoryRepository.findAmazonCategoryByMarketplaceIdAndCategoryId(marketplaceId, id);
    }
    public List<AmazonCategory> getListByMarketplaceIdAndCategoryName(String marketplaceId, String name) {
        return amazonCategoryRepository.findAllByMarketplaceIdAndName(marketplaceId, name);
    }
    public List<AmazonCategory> getListByMarketplaceIdAndCategoryId(String marketplaceId, String id) {
        return amazonCategoryRepository.findAllByMarketplaceIdAndCategoryId(marketplaceId,id);
    }

    public List<AmazonCategory> querySubCategoriesLevel2(AmazonCategory vO) {
//        return amazonCategoryRepository.findAll(Example.of(vO));
        var level1= amazonCategoryRepository.findAllByMarketplaceIdAndParentId(vO.getMarketplaceId(), vO.getCategoryId());
        var parentIds = level1.stream().map(AmazonCategory::getCategoryId).toList();
        var level2 = amazonCategoryRepository.findAllByMarketplaceIdAndParentIdIn(vO.getMarketplaceId(), parentIds);
        for(var pg :level1){
            pg.setChildren(level2.stream().filter(s-> Objects.equals(s.getParentId(), pg.getCategoryId())).collect(Collectors.toList()));
        }
        return level1;
    }

    @Cacheable(value = "amazoncategory",key = "#vO.marketplaceId+'_'+#vO.name")
    public List<AmazonCategory> findAllChildCategories(AmazonCategory vO) {
        // 查询所有节点
        var nodes = amazonCategoryRepository.findAllChildCategories(vO.getMarketplaceId(), vO.getCategoryId());
        // 拼装结果
        List<AmazonCategory> bmsMenuTree = new ArrayList<>();
        // 用来存储节点的子元素map
        Map<String, AmazonCategory> childBmsMenuMap = new LinkedHashMap<>();
        for (AmazonCategory menuVO : nodes) {
            childBmsMenuMap.put(menuVO.getCategoryId(), menuVO);
        }
        for (var bmsMenuId : childBmsMenuMap.keySet()) {
            AmazonCategory menuVO = childBmsMenuMap.get(bmsMenuId);
            var parentId = menuVO.getParentId();
            if (parentId == null) {
                bmsMenuTree.add(menuVO);
            } else {
                AmazonCategory parentMenuVO = childBmsMenuMap.get(parentId);
                if (parentMenuVO.getChildren() == null) {
                    parentMenuVO.setChildren(new ArrayList<>());
                }
                parentMenuVO.getChildren().add(menuVO);
            }
        }

        return bmsMenuTree;
    }
    public List<AmazonCategory> queryLevel2TreeByCategoryName(AmazonCategory vO) {
        var nodes = amazonCategoryRepository.findAllByMarketplaceIdAndNameLike(vO.getMarketplaceId(), vO.getName());
        for(var node:nodes){
            var list = querySubCategoriesLevel2(node);
        }
        return null;
    }
}