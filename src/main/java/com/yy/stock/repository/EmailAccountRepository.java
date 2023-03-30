package com.yy.stock.repository;

import com.yy.stock.entity.EmailAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long>, JpaSpecificationExecutor<EmailAccount> {

}