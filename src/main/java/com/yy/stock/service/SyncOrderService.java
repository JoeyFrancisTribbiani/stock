package com.yy.stock.service;

import com.yy.stock.entity.SyncOrder;
import com.yy.stock.repository.SyncOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SyncOrderService {

    @Autowired
    private SyncOrderRepository syncOrderRepository;

    public Long save(SyncOrder bean) {
        bean = syncOrderRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        syncOrderRepository.deleteById(id);
    }

    public void update(Long id, SyncOrder vO) {
        SyncOrder bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        syncOrderRepository.save(bean);
    }

    public SyncOrder getById(Long id) {
        SyncOrder original = requireOne(id);
        return original;
    }

    public Page<SyncOrder> query(SyncOrder vO) {
        throw new UnsupportedOperationException();
    }

    private SyncOrder requireOne(Long id) {
        return syncOrderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public List<SyncOrder> getOpeningServers() {
        return syncOrderRepository.findAllBySyncSwitchIs(true);
    }
}
