package com.yy.stock.service;

import com.yy.stock.dto.StatusDTO;
import com.yy.stock.entity.Status;
import com.yy.stock.repository.StatusRepository;
import com.yy.stock.vo.StatusQueryVO;
import com.yy.stock.vo.StatusUpdateVO;
import com.yy.stock.vo.StatusVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public String save(StatusVO vO) {
        Status bean = new Status();
        BeanUtils.copyProperties(vO, bean);
        bean = statusRepository.save(bean);
        return bean.getId();
    }

    public void delete(String id) {
        statusRepository.deleteById(id);
    }

    public void update(String id, StatusUpdateVO vO) {
        Status bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        statusRepository.save(bean);
    }

    public StatusDTO getById(String id) {
        Status original = requireOne(id);
        return toDTO(original);
    }

    public Page<StatusDTO> query(StatusQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private StatusDTO toDTO(Status original) {
        StatusDTO bean = new StatusDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Status requireOne(String id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
