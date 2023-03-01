package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.OrdersFulfilledShipmentsReportDTO;
import com.yy.stock.adaptor.amazon.entity.OrdersFulfilledShipmentsReport;
import com.yy.stock.adaptor.amazon.repository.OrdersFulfilledShipmentsReportRepository;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.OrdersFulfilledShipmentsReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrdersFulfilledShipmentsReportService {

    @Autowired
    private OrdersFulfilledShipmentsReportRepository ordersFulfilledShipmentsReportRepository;

    public String save(OrdersFulfilledShipmentsReportVO vO) {
        OrdersFulfilledShipmentsReport bean = new OrdersFulfilledShipmentsReport();
        BeanUtils.copyProperties(vO, bean);
        bean = ordersFulfilledShipmentsReportRepository.save(bean);
        return bean.getAmazonOrderId();
    }

    public void delete(String id) {
        ordersFulfilledShipmentsReportRepository.deleteById(id);
    }

    public void update(String id, OrdersFulfilledShipmentsReportUpdateVO vO) {
        OrdersFulfilledShipmentsReport bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        ordersFulfilledShipmentsReportRepository.save(bean);
    }

    public OrdersFulfilledShipmentsReportDTO getById(String id) {
        OrdersFulfilledShipmentsReport original = requireOne(id);
        return toDTO(original);
    }

    public Page<OrdersFulfilledShipmentsReportDTO> query(OrdersFulfilledShipmentsReportQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private OrdersFulfilledShipmentsReportDTO toDTO(OrdersFulfilledShipmentsReport original) {
        OrdersFulfilledShipmentsReportDTO bean = new OrdersFulfilledShipmentsReportDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private OrdersFulfilledShipmentsReport requireOne(String id) {
        return ordersFulfilledShipmentsReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
