package com.kakaopay.support.repository;

import com.kakaopay.support.entity.BankSupport;
import com.kakaopay.support.model.search.RequestBankSupport;

import java.util.List;

public interface BankSupportRepositoryCustom {
    List<BankSupport> search(RequestBankSupport request);
}
