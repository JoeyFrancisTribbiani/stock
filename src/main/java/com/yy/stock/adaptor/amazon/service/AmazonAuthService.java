package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmazonAuthDTO;
import com.yy.stock.adaptor.amazon.entity.AmazonAuth;
import com.yy.stock.adaptor.amazon.repository.AmazonAuthRepository;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmazonAuthVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AmazonAuthService {

    @Autowired
    private AmazonAuthRepository amazonAuthRepository;

    public Long save(AmazonAuthVO vO) {
        AmazonAuth bean = new AmazonAuth();
        BeanUtils.copyProperties(vO, bean);
        bean = amazonAuthRepository.save(bean);
        return bean.getId();
    }

    public void delete(String id) {
        amazonAuthRepository.deleteById(id);
    }

    public void update(String id, AmazonAuthUpdateVO vO) {
        AmazonAuth bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amazonAuthRepository.save(bean);
    }

    public AmazonAuthDTO getById(String id) {
        AmazonAuth original = requireOne(id);
        return toDTO(original);
    }

    public Page<AmazonAuthDTO> query(AmazonAuthQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmazonAuthDTO toDTO(AmazonAuth original) {
        AmazonAuthDTO bean = new AmazonAuthDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmazonAuth requireOne(String id) {
        return amazonAuthRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
