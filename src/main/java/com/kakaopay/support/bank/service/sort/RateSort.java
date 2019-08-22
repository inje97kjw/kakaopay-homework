package com.kakaopay.support.bank.service.sort;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.model.search.Rate;
import com.kakaopay.support.bank.util.ConvertUtil;

import java.util.List;

public class RateSort implements CustomSort {
    @Override
    public List<BankSupport> excute(List<BankSupport> list) {
        list.sort((o1, o2) -> {
            Rate rate1 = ConvertUtil.rateConvertToNumber(o1.getRate());
            Rate rate2 = ConvertUtil.rateConvertToNumber(o2.getRate());
            if (rate1.getMin() > rate2.getMin()) {
                return 1;
            } else if (rate1.getMin() < rate2.getMin()) {
                return -1;
            } else {
                return 0;
            }
        });
        return list;
    }
}
