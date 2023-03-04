package com.yy.stock.service;

import com.yy.stock.entity.Platform;
import com.yy.stock.repository.PlatformRepository;
import com.yy.stock.vo.PlatformQueryVO;
import com.yy.stock.vo.PlatformUpdateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    public BigInteger save(Platform bean) {
        bean = platformRepository.save(bean);
        return bean.getId();
    }

    public void delete(BigInteger id) {
        platformRepository.deleteById(id);
    }

    public void update(BigInteger id, PlatformUpdateVO vO) {
        Platform bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        platformRepository.save(bean);
    }

    public Platform getById(BigInteger id) {
        Platform original = requireOne(id);
        return original;
    }

    public Page<Platform> query(PlatformQueryVO vO) {
        throw new UnsupportedOperationException();
    }


    private Platform requireOne(BigInteger id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
