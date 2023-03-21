package com.yy.stock.service;

import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.entity.Supplier;
import com.yy.stock.repository.SupplierRepository;
import com.yy.stock.vo.SupplierQueryVO;
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

    public BigInteger save(Supplier bean) {
        bean = supplierRepository.save(bean);
        return bean.getId();
    }

    public void delete(BigInteger id) {
        supplierRepository.deleteById(id);
    }

    public void update(BigInteger id, Supplier vO) {
        Supplier bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        supplierRepository.save(bean);
    }

    public Supplier getById(BigInteger id) {
        Supplier original = requireOne(id);
        return original;
    }

    public Supplier getByAmazonOrderInfo(OrderItemAdaptorInfoDTO order) {
        Supplier supplier = supplierRepository.findByAmazonAuthIdAndMarketplaceIdAndAmazonSku(order.getAuthid(), order.getMarketplaceId(), order.getSku());
        return supplier;
    }

    public Supplier getBySku(BigInteger amazonAuthId, String marketplaceId, String amazonSku) {
        Supplier supplier = supplierRepository.findByAmazonAuthIdAndMarketplaceIdAndAmazonSku(amazonAuthId, marketplaceId, amazonSku);
        return supplier;
    }

    public Supplier createEmptySupplier(BigInteger amazonAuthId, String marketplaceId, String amazonSku) {
        var supplier = new Supplier()
                .setAmazonAuthId(amazonAuthId)
                .setMarketplaceId(marketplaceId)
                .setAmazonSku(amazonSku)
                .setName("")
                .setPlatformId(new BigInteger("0"))
                .setAmazonName("")
                .setAvailable(false)
                .setPrice("0")
                .setShopName("")
                .setUrl("");
        return supplierRepository.save(supplier);
    }

    public Page<Supplier> query(SupplierQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private Supplier requireOne(BigInteger id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
