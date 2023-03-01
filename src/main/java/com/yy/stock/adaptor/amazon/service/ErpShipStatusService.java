package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.ErpShipStatusDTO;
import com.yy.stock.adaptor.amazon.entity.ErpShipStatus;
import com.yy.stock.adaptor.amazon.repository.ErpShipStatusRepository;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusQueryVO;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusUpdateVO;
import com.yy.stock.adaptor.amazon.vo.ErpShipStatusVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ErpShipStatusService {

    @Autowired
    private ErpShipStatusRepository erpShipStatusRepository;

    public String save(ErpShipStatusVO vO) {
        ErpShipStatus bean = new ErpShipStatus();
        BeanUtils.copyProperties(vO, bean);
        bean = erpShipStatusRepository.save(bean);
        return bean.getStatus();
    }

    public void delete(String id) {
        erpShipStatusRepository.deleteById(id);
    }

    public void update(String id, ErpShipStatusUpdateVO vO) {
        ErpShipStatus bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        erpShipStatusRepository.save(bean);
    }

    public ErpShipStatusDTO getById(String id) {
        ErpShipStatus original = requireOne(id);
        return toDTO(original);
    }

    public Page<ErpShipStatusDTO> query(ErpShipStatusQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ErpShipStatusDTO toDTO(ErpShipStatus original) {
        ErpShipStatusDTO bean = new ErpShipStatusDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ErpShipStatus requireOne(String id) {
        return erpShipStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
