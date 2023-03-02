package com.yy.stock.service;

import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.dto.SupplierDTO;
import com.yy.stock.entity.Supplier;
import com.yy.stock.repository.SupplierRepository;
import com.yy.stock.vo.SupplierQueryVO;
import com.yy.stock.vo.SupplierUpdateVO;
import com.yy.stock.vo.SupplierVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Long save(SupplierVO vO) {
        Supplier bean = new Supplier();
        BeanUtils.copyProperties(vO, bean);
        bean = supplierRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    public void update(Long id, SupplierUpdateVO vO) {
        Supplier bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        supplierRepository.save(bean);
    }

    public SupplierDTO getById(Long id) {
        Supplier original = requireOne(id);
        return toDTO(original);
    }

    public SupplierDTO getByAmazonOrderInfo(OrdersReport order) {
        Supplier supplier = supplierRepository.findByMarketplaceIdAndAmazonSku(order.getMarketplaceId(), order.getSku());
        return toDTO(supplier);
    }

    public Page<SupplierDTO> query(SupplierQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SupplierDTO toDTO(Supplier original) {
        SupplierDTO bean = new SupplierDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Supplier requireOne(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
