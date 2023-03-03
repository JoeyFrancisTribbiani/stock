package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.adaptor.amazon.repository.AmzOrdersAddressRepository;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrdersAddressVO;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AmzOrdersAddressService {

    @Autowired
    private AmzOrdersAddressRepository amzOrdersAddressRepository;

    public String save(AmzOrdersAddressVO vO) {
        AmzOrdersAddress bean = new AmzOrdersAddress();
        BeanUtils.copyProperties(vO, bean);
        bean = amzOrdersAddressRepository.save(bean);
        return bean.getAmazonOrderId();
    }

    public void delete(String id) {
        amzOrdersAddressRepository.deleteById(id);
    }

    public void update(String id, AmzOrdersAddressUpdateVO vO) {
        AmzOrdersAddress bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amzOrdersAddressRepository.save(bean);
    }

    public AmzOrdersAddressDTO getById(String id) {
        AmzOrdersAddress original = requireOne(id);
        return toDTO(original);
    }

    public AmzOrdersAddress getByOrderInfo(OrderItemAdaptorInfoDTO orderToStock) {
        AmzOrdersAddress ordersAddress = amzOrdersAddressRepository.findByMarketplaceIdAndAmazonAuthIdAndAmazonOrderId(orderToStock.getMarketplaceId(), orderToStock.getAuthid(), orderToStock.getOrderid());
        return ordersAddress;
    }

    public Page<AmzOrdersAddressDTO> query(AmzOrdersAddressQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmzOrdersAddressDTO toDTO(AmzOrdersAddress original) {
        AmzOrdersAddressDTO bean = new AmzOrdersAddressDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmzOrdersAddress requireOne(String id) {
        return amzOrdersAddressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
