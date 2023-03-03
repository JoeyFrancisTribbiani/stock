package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmzOrderItemDTO;
import com.yy.stock.adaptor.amazon.entity.AmzOrderItem;
import com.yy.stock.adaptor.amazon.entity.AmzOrderItemUPK;
import com.yy.stock.adaptor.amazon.repository.AmzOrderItemRepository;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzOrderItemVO;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AmzOrderItemService {

    @Autowired
    private AmzOrderItemRepository amzOrderItemRepository;

    public List<OrderItemAdaptorInfoDTO> get3DaysUnshipped() {
        return amzOrderItemRepository.find3DaysUnshippedOrders();
    }

    public AmzOrderItemUPK save(AmzOrderItemVO vO) {
        AmzOrderItem bean = new AmzOrderItem();
        BeanUtils.copyProperties(vO, bean);
        bean = amzOrderItemRepository.save(bean);
        return bean.getAmzOrderItemUPK();
    }

    public void delete(String id) {
        amzOrderItemRepository.deleteById(id);
    }

    public void update(String id, AmzOrderItemUpdateVO vO) {
        AmzOrderItem bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amzOrderItemRepository.save(bean);
    }

    public AmzOrderItemDTO getById(String id) {
        AmzOrderItem original = requireOne(id);
        return toDTO(original);
    }

    public Page<AmzOrderItemDTO> query(AmzOrderItemQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmzOrderItemDTO toDTO(AmzOrderItem original) {
        AmzOrderItemDTO bean = new AmzOrderItemDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmzOrderItem requireOne(String id) {
        return amzOrderItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
