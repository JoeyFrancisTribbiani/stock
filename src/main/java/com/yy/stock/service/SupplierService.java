package com.yy.stock.service;

import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.Supplier;
import com.yy.stock.repository.SupplierRepository;
import com.yy.stock.vo.SupplierQueryVO;
import com.yy.stock.vo.SupplierUpdateVO;
import com.yy.stock.vo.SupplierVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public BigInteger save(SupplierVO vO) {
        Supplier bean = new Supplier();
        BeanUtils.copyProperties(vO, bean);
        bean = supplierRepository.save(bean);
        return bean.getId();
    }

    public void delete(BigInteger id) {
        supplierRepository.deleteById(id);
    }

    public void update(BigInteger id, SupplierUpdateVO vO) {
        Supplier bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        supplierRepository.save(bean);
    }

    public Supplier getById(BigInteger id) {
        Supplier original = requireOne(id);
        return original;
    }

    public Supplier getByAmazonOrderInfo(OrderItemAdaptorInfoDTO order) {
        Supplier supplier = supplierRepository.findByMarketplaceIdAndAmazonSku(order.getMarketplaceId(), order.getSku());
        return supplier;

    }

    public Page<Supplier> query(SupplierQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private Supplier requireOne(BigInteger id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
