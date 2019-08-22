package com.kakaopay.support.bank.service.sort;

import com.kakaopay.support.bank.entity.BankSupport;

import java.util.List;

public interface CustomSort {
    List<BankSupport> excute(List<BankSupport> list);
}
