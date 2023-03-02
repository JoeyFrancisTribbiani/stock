package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.adaptor.amazon.repository.OrdersReportRepository;
import com.yy.stock.adaptor.amazon.vo.OrdersReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.OrdersReportVO;
import com.yy.stock.config.AmazonOrderStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrdersReportService {

    @Autowired
    private OrdersReportRepository ordersReportRepository;

    public String save(OrdersReportVO vO) {
        OrdersReport bean = new OrdersReport();
        BeanUtils.copyProperties(vO, bean);
        bean = ordersReportRepository.save(bean);
        return bean.getId();
    }

    public List<OrdersReport> getUnshiped() {
        return ordersReportRepository
                .findAllByOrderStatus(AmazonOrderStatusEnum.Unshipped.name());
    }

    public void delete(String id) {
        ordersReportRepository.deleteById(id);
    }

    public void update(String id, OrdersReportUpdateVO vO) {
        OrdersReport bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        ordersReportRepository.save(bean);
    }

    public OrdersReportDTO getById(String id) {
        OrdersReport original = requireOne(id);
        return toDTO(original);
    }

    public Page<OrdersReportDTO> query(OrdersReportQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private OrdersReportDTO toDTO(OrdersReport original) {
        OrdersReportDTO bean = new OrdersReportDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private OrdersReport requireOne(String id) {
        return ordersReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
