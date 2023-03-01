package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmzOrderBuyerShipAddressDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrderBuyerShipAddress;
import com.yy.stock.adaptor.amazon.repository.AmzOrderBuyerShipAddressRepository;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderBuyerShipAddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AmzOrderBuyerShipAddressService {

    @Autowired
    private AmzOrderBuyerShipAddressRepository amzOrderBuyerShipAddressRepository;

    public String save(AmzOrderBuyerShipAddressVO vO) {
        AmzOrderBuyerShipAddress bean = new AmzOrderBuyerShipAddress();
        BeanUtils.copyProperties(vO, bean);
        bean = amzOrderBuyerShipAddressRepository.save(bean);
        return bean.getId();
    }

    public void delete(String id) {
        amzOrderBuyerShipAddressRepository.deleteById(id);
    }

    public void update(String id, AmzOrderBuyerShipAddressUpdateVO vO) {
        AmzOrderBuyerShipAddress bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amzOrderBuyerShipAddressRepository.save(bean);
    }

    public AmzOrderBuyerShipAddressDTO getById(String id) {
        AmzOrderBuyerShipAddress original = requireOne(id);
        return toDTO(original);
    }

    public Page<AmzOrderBuyerShipAddressDTO> query(AmzOrderBuyerShipAddressQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmzOrderBuyerShipAddressDTO toDTO(AmzOrderBuyerShipAddress original) {
        AmzOrderBuyerShipAddressDTO bean = new AmzOrderBuyerShipAddressDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmzOrderBuyerShipAddress requireOne(String id) {
        return amzOrderBuyerShipAddressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
