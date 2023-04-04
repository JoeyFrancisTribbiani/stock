package com.yy.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CEmailService extends EmailAccountService {
    @Autowired
    protected cSupplierService supplierService;

    public CEmailService() {
        super();

    }
}
