package com.yy.stock.service;

import com.yy.stock.dto.PlatformDTO;
import com.yy.stock.entity.Platform;
import com.yy.stock.repository.PlatformRepository;
import com.yy.stock.vo.PlatformQueryVO;
import com.yy.stock.vo.PlatformUpdateVO;
import com.yy.stock.vo.PlatformVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    public Long save(PlatformVO vO) {
        Platform bean = new Platform();
        BeanUtils.copyProperties(vO, bean);
        bean = platformRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        platformRepository.deleteById(id);
    }

    public void update(Long id, PlatformUpdateVO vO) {
        Platform bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        platformRepository.save(bean);
    }

    public Platform getById(Long id) {
        Platform original = requireOne(id);
        return original;
    }

    public Page<PlatformDTO> query(PlatformQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PlatformDTO toDTO(Platform original) {
        PlatformDTO bean = new PlatformDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Platform requireOne(Long id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
