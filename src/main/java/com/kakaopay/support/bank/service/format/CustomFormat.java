package com.kakaopay.support.bank.service.format;

import com.kakaopay.support.bank.entity.BankSupport;

public interface CustomFormat {
    CustomFormat generateFormat(BankSupport bankSupport);
}
