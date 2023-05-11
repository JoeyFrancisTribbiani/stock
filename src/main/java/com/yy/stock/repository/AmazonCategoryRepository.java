package com.yy.stock.repository;

import com.yy.stock.entity.AmazonCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AmazonCategoryRepository extends JpaRepository<AmazonCategory, Integer>, JpaSpecificationExecutor<AmazonCategory> {

    AmazonCategory findAmazonCategoryByMarketplaceIdAndCategoryId(String marketplaceId, String id);

    List<AmazonCategory> findAllByMarketplaceIdAndName(String marketplaceId, String name);

    List<AmazonCategory> findAllByMarketplaceIdAndCategoryId(String marketplaceId, String id);

    List<AmazonCategory> findAllByMarketplaceIdAndParentId(String marketplaceId, String id);

    List<AmazonCategory> findAllByMarketplaceIdAndNameLike(String marketplaceId, String name);

    List<AmazonCategory> findAllByMarketplaceIdAndParentIdIn(String marketplaceId, List<String> parentIds);

    @Query(nativeQuery = true, value = " select t2.* \n" +
            " from (\n" +
            " \tSELECT @a AS _id ,(SELECT @a := parent_id FROM amazon_category WHERE category_id = _id limit 1) AS parent_id\n" +
            "   FROM \n" +
            "        (SELECT @a := ?2) va, \n" +
            "        amazon_category\n" +
            "   WHERE @a != 'Root' \n" +
            "    ) t1\n" +
            " join amazon_category t2 on t1._id=t2.category_id where t2.marketplace_id=?1 and  t2.category_id!=?2")
    List<AmazonCategory> findAllParents(String marketplaceId, String categoryId);


    @Query(nativeQuery = true, value = "WITH RECURSIVE r_t AS ( SELECT s1.* FROM amazon_category s1 WHERE s1.category_id = :categoryId UNION ALL SELECT s2.* FROM amazon_category s2 INNER JOIN r_t ON r_t.category_id = s2.parent_id where s2.marketplace_id= :marketplaceId ) SELECT 	* FROM r_t")
    List<AmazonCategory> findAllChildCategories(@Param("marketplaceId") String marketplaceId, @Param("categoryId") String categoryId);
}