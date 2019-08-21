package com.kakaopay.support.bank.repository;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.model.search.RequestBankSupport;

import java.util.List;

public interface BankSupportRepositoryCustom {
    List<BankSupport> search(RequestBankSupport request);
}
