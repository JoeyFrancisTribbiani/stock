package com.yy.stock.service;

import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.StatusDTO;
import com.yy.stock.entity.Status;
import com.yy.stock.repository.StatusRepository;
import com.yy.stock.vo.StatusQueryVO;
import com.yy.stock.vo.StatusUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public String save(Status s) {
        statusRepository.save(s);
        return s.getId();
    }

    public void delete(String id) {
        statusRepository.deleteById(id);
    }

    public Status getOrCreate(String marketplaceId, String amazonOrderId) {
        Status status = statusRepository.findFirstBymarketplaceIdAndAmazonOrderId(marketplaceId, amazonOrderId);
        if (status == null) {
            Status toCreate = new Status();
            toCreate.setMarketplaceId(marketplaceId);
            toCreate.setAmazonOrderId(amazonOrderId);
            toCreate.setStatus(StatusEnum.unstocked.ordinal());
            statusRepository.save(toCreate);

            status = toCreate;
        }
        return status;
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

    public boolean setStockingStatus(Status status) {
        try {
            status.setStatus(StatusEnum.stocking.ordinal());
            statusRepository.save(status);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
