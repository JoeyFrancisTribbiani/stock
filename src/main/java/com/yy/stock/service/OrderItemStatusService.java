package com.yy.stock.service;

import com.yy.stock.dto.OrderItemStatusDTO;
import com.yy.stock.entity.OrderItemStatus;
import com.yy.stock.repository.OrderItemStatusRepository;
import com.yy.stock.vo.OrderItemStatusQueryVO;
import com.yy.stock.vo.OrderItemStatusUpdateVO;
import com.yy.stock.vo.OrderItemStatusVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderItemStatusService {

    @Autowired
    private OrderItemStatusRepository orderItemStatusRepository;

    public String save(OrderItemStatusVO vO) {
        OrderItemStatus bean = new OrderItemStatus();
        BeanUtils.copyProperties(vO, bean);
        bean = orderItemStatusRepository.save(bean);
        return bean.getId();
    }

    public void delete(String id) {
        orderItemStatusRepository.deleteById(id);
    }

    public void update(String id, OrderItemStatusUpdateVO vO) {
        OrderItemStatus bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        orderItemStatusRepository.save(bean);
    }

    public OrderItemStatusDTO getById(String id) {
        OrderItemStatus original = requireOne(id);
        return toDTO(original);
    }

    public Page<OrderItemStatusDTO> query(OrderItemStatusQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private OrderItemStatusDTO toDTO(OrderItemStatus original) {
        OrderItemStatusDTO bean = new OrderItemStatusDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private OrderItemStatus requireOne(String id) {
        return orderItemStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
