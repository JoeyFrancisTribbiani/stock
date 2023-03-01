package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmzOrderMainDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrderMain;
import com.yy.stock.adaptor.amazon.repository.AmzOrderMainRepository;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderMainVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AmzOrderMainService {

    @Autowired
    private AmzOrderMainRepository amzOrderMainRepository;

    public String save(AmzOrderMainVO vO) {
        AmzOrderMain bean = new AmzOrderMain();
        BeanUtils.copyProperties(vO, bean);
        bean = amzOrderMainRepository.save(bean);
        return bean.getAmazonOrderId();
    }

    public void delete(String id) {
        amzOrderMainRepository.deleteById(id);
    }

    public void update(String id, AmzOrderMainUpdateVO vO) {
        AmzOrderMain bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amzOrderMainRepository.save(bean);
    }

//    public AmzOrderMainDTO getById(String id) {
//        AmzOrderMain original = requireOne(id);
//        return toDTO(original);
//    }

    public List<AmzOrderMainDTO> getUnshiped() {
        return amzOrderMainRepository
                .findAmzOrderMainsByOrderStatusIs("Unshipped");
//        AmzOrderMain order = new AmzOrderMain();
//        order.setOrderStatus("Unshipped");
//        Example<AmzOrderMain> example = Example.of(order);
//
//        List<AmzOrderMain> list = amzOrderMainRepository.findAll(example);
//        List<AmzOrderMainDTO> result = new ArrayList<>();
//        for (AmzOrderMain amzOrder :
//                list) {
//            AmzOrderMainDTO dto = toDTO(amzOrder);
//            result.add(dto);
//        }
//        return result;
    }

    public Page<AmzOrderMainDTO> query(AmzOrderMainQueryVO vO) {
        throw new UnsupportedOperationException();
    }

//    private AmzOrderMainDTO toDTO(AmzOrderMain original) {
//        AmzOrderMainDTO bean = new AmzOrderMainDTO();
//        BeanUtils.copyProperties(original, bean);
//        return bean;
//    }

    private AmzOrderMain requireOne(String id) {
        return amzOrderMainRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
