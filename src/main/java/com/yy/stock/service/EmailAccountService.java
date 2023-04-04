package com.yy.stock.service;

import com.yy.stock.entity.EmailAccount;
import com.yy.stock.repository.EmailAccountRepository;
import com.yy.stock.vo.EmailAccountListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

;

@Service
public class EmailAccountService {

    @Autowired
    protected SupplierService supplierService;
    @Autowired
    private EmailAccountRepository emailAccountRepository;

    public Long save(EmailAccount bean) {
        bean = emailAccountRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        emailAccountRepository.deleteById(id);
    }

    public void update(Long id, EmailAccount bean) {
        emailAccountRepository.save(bean);
    }

    public EmailAccount getById(Long id) {
        EmailAccount original = requireOne(id);
        return original;
    }

    public Page<EmailAccount> query(EmailAccountListQuery vO) {
        return emailAccountRepository.findAll(vO);
    }


    private EmailAccount requireOne(Long id) {
        return emailAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public boolean uploadEmailAccountFile(EmailAccount vO) {
        return false;
    }
}
