package com.kakaopay.support.bank.repository;

import com.kakaopay.support.bank.entity.BankSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankSupportRepository extends JpaRepository<BankSupport, Long>, BankSupportRepositoryCustom {
    BankSupport findByRegionCodeCode(String regionCode);
}
