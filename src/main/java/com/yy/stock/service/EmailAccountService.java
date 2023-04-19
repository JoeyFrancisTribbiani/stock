package com.yy.stock.service;

import com.yy.stock.entity.EmailAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.repository.EmailAccountRepository;
import com.yy.stock.vo.EmailAccountListQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmailAccountService {

    @Autowired
    protected SupplierService supplierService;
    @Autowired
    protected BuyerAccountService buyerAccountService;
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
        return emailAccountRepository.findAll(vO.toSpecification(), vO.toPageRequest());
    }


    private EmailAccount requireOne(Long id) {
        return emailAccountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public boolean uploadEmailAccountFile(EmailAccount vO) {
        return false;
    }

    public void readCsv(MultipartFile csvFile) throws IOException {
        List<String> lines = IOUtils.readLines(csvFile.getInputStream(), "UTF-8");
        var list = new ArrayList<EmailAccount>();
        for (String line : lines) {
            String[] split = line.split("----");
            EmailAccount emailAccount = new EmailAccount();
            emailAccount.setEmail(split[0]);
            emailAccount.setPassword(split[1]);
            emailAccount.setVerifyEmail(split[2]);
            list.add(emailAccount);
        }
        emailAccountRepository.saveAll(list);
    }

    public List<EmailAccount> getUnregisteredEmail(Platform platform) {
        var buyerAccountList = platform.getBuyerAccounts();
        var allEmailAccountList = emailAccountRepository.findAll();
        var emailAccountList = new ArrayList<EmailAccount>();
        for (var emailAccount : allEmailAccountList) {
            for (var buyerAccount : buyerAccountList) {
                if (!emailAccount.getEmail().equals(buyerAccount.getEmail())) {
                    emailAccountList.add(emailAccount);
                }
            }
        }
        return emailAccountList;
    }
}
