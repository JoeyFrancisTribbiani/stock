package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.ShopDTO;
import com.yy.stock.adaptor.amazon.entity.Shop;
import com.yy.stock.adaptor.amazon.repository.ShopRepository;
import com.yy.stock.adaptor.amazon.vo.ShopQueryVO;
import com.yy.stock.adaptor.amazon.vo.ShopUpdateVO;
import com.yy.stock.adaptor.amazon.vo.ShopVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public String save(ShopVO vO) {
        Shop bean = new Shop();
        BeanUtils.copyProperties(vO, bean);
        bean = shopRepository.save(bean);
        return bean.getId();
    }

    public void delete(String id) {
        shopRepository.deleteById(id);
    }

    public void update(String id, ShopUpdateVO vO) {
        Shop bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        shopRepository.save(bean);
    }

    public ShopDTO getById(String id) {
        Shop original = requireOne(id);
        return toDTO(original);
    }

    public Page<ShopDTO> query(ShopQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ShopDTO toDTO(Shop original) {
        ShopDTO bean = new ShopDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Shop requireOne(String id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
