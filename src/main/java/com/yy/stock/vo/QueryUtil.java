package com.yy.stock.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    public static <T> Specification<T> parse(QueryConditions conditions) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateItems = new ArrayList<>();
            if (conditions == null) {
                return null;
            }
            conditions.forEach(item -> {
                Predicate predicateItem;
                switch (item.getMatchType()) {
                    case eq:
                        predicateItem = criteriaBuilder.equal(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case neq:
                        predicateItem = criteriaBuilder.notEqual(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case gt:
                        predicateItem = criteriaBuilder.greaterThan(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case gte:
                        predicateItem = criteriaBuilder.greaterThanOrEqualTo(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case lt:
                        predicateItem = criteriaBuilder.lessThan(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case lte:
                        predicateItem = criteriaBuilder.lessThanOrEqualTo(root.get(item.getField()).as(String.class), item.getVal());
                        break;
                    case like:
                        predicateItem = criteriaBuilder.like(root.get(item.getField()).as(String.class), "%" + item.getVal() + "%");
                        break;
                    case notLike:
                        predicateItem = criteriaBuilder.notLike(root.get(item.getField()).as(String.class), "%" + item.getVal() + "%");
                        break;
                    case isNull:
                        predicateItem = criteriaBuilder.isNull(root.get(item.getField()).as(String.class));
                        break;
                    case isNotNull:
                        predicateItem = criteriaBuilder.isNotNull(root.get(item.getField()).as(String.class));
                        break;
                    default:
                        throw new RuntimeException("筛选条件MatchType不合法");
                }
                predicateItems.add(predicateItem);
            });
            return criteriaBuilder.and(predicateItems.toArray(new Predicate[0]));
        };
    }


    public static <T> Page<T> toPage(org.springframework.data.domain.Page<T> queryPage, PageRequest pageRequest) {
        var page = new Page<T>(pageRequest.getPageNumber(), queryPage.getSize(), queryPage.getTotalElements(), true);
        page.setOrders(new ArrayList<>());
        page.setPages(queryPage.getTotalPages());
        page.setRecords(queryPage.getContent());
        return page;
    }
}