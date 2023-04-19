package com.yy.stock.adaptor.amazon.api.pojo.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.stock.adaptor.amazon.api.pojo.entity.BizException;
import com.yy.stock.vo.QueryConditions;
import com.yy.stock.vo.QueryUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
public class BasePageQuery {

    private int currentpage = 1;

    private int pagesize = 1000;

    private String sort = "";

    private String order = "";
    private Object where;

    private Map<String, String> paramother = new HashMap<String, String>();

    // 将where解析成预定义的筛选条件对象(代码在下文给出)
    public QueryConditions parseWhere() {
        return JSON.parseObject(JSON.toJSONString(where), QueryConditions.class);
    }

    // 将where解析成JPA支持的Specification对象
    public <T> Specification<T> toSpecification() {
        return QueryUtil.parse(parseWhere()); // QueryUtil是关键代码，下文亦给出
    }

    public PageRequest toPageRequest() {
        PageRequest pageRequest = PageRequest.of(currentpage - 1, pagesize);

        if (StrUtil.isNotBlank(sort)) {
            if (StrUtil.isBlank(order)) {
                order = "asc";
            }
            List<Sort.Order> orderList = new ArrayList<>();
            String[] sortarray = sort.split(",");
            String[] orderarray = order.split(",");
            for (int i = 0; i < sortarray.length; i++) {
                String msort = sortarray[i];
                String morder = "asc";
                if (i < orderarray.length) {
                    morder = orderarray[i];
                }
                Sort.Order order = new Sort.Order("asc".equals(morder.toLowerCase()) ? Sort.Direction.ASC : Sort.Direction.DESC, msort);
                orderList.add(order);
            }
            pageRequest = PageRequest.of(currentpage - 1, pagesize, Sort.by(orderList));
        }
        return pageRequest;
    }

    public <T> Page<T> getPage() {
        Page<T> page = new Page<T>(currentpage, pagesize);
        if (StrUtil.isNotBlank(sort)) {
            if (StrUtil.isBlank(order)) {
                order = "asc";
            }
            List<OrderItem> orderlist = new ArrayList<OrderItem>();
            String[] sortarray = sort.split(",");
            String[] orderarray = order.split(",");
            for (int i = 0; i < sortarray.length; i++) {
                String msort = sortarray[i];
                String morder = "asc";
                if (i < orderarray.length) {
                    morder = orderarray[i];
                }
                orderlist.add(new OrderItem(msort, "asc".equals(morder.toLowerCase())));
            }
            page.addOrder(orderlist);
        }
        return page;
    }


    int compareTo(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1 instanceof BigDecimal) {
            BigDecimal v1 = (BigDecimal) o1;
            BigDecimal v2 = (BigDecimal) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof String) {
            String v1 = (String) o1;
            String v2 = (String) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof Date) {
            Date v1 = (Date) o1;
            Date v2 = (Date) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof LocalDateTime) {
            LocalDateTime v1 = (LocalDateTime) o1;
            LocalDateTime v2 = (LocalDateTime) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof Integer) {
            Integer v1 = (Integer) o1;
            Integer v2 = (Integer) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof Long) {
            Long v1 = (Long) o1;
            Long v2 = (Long) o2;
            return v1.compareTo(v2);
        } else if (o1 instanceof Float) {
            Float v1 = (Float) o1;
            Float v2 = (Float) o2;
            return v1.compareTo(v2);
        } else {
            throw new BizException("排序字段类型无法匹配，请联系管理员");
        }
    }

    public <T> IPage<T> getListPage(List<T> mylist) {
        Collections.sort(mylist, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                Map<String, Object> m1 = BeanUtil.beanToMap(o1);
                Map<String, Object> m2 = BeanUtil.beanToMap(o2);
                if (order != null && order.equals("desc")) {
                    return compareTo(m2.get(sort), m1.get(sort));
                } else {
                    return compareTo(m1.get(sort), m2.get(sort));
                }
            }
        });


        IPage<T> result = new Page<T>(currentpage, pagesize, mylist.size());
        if (mylist.size() > 0) {
            result.setRecords(mylist.subList((currentpage - 1) * pagesize, mylist.size() > currentpage * pagesize ? currentpage * pagesize : mylist.size()));
        }
        return result;
    }

}
