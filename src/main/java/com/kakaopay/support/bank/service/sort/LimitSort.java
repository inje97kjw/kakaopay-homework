package com.kakaopay.support.bank.service.sort;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.util.ConvertUtil;

import java.util.List;

public class LimitSort implements CustomSort {
    @Override
    public List<BankSupport> excute(List<BankSupport> list) {
        list.sort((o1, o2) -> {
            long limit1 = ConvertUtil.limitConvertToNumber(o1.getLimit());
            long limit2 = ConvertUtil.limitConvertToNumber(o2.getLimit());
            double rate1 = ConvertUtil.rateAvg(o1.getRate());
            double rate2 = ConvertUtil.rateAvg(o2.getRate());
            if (limit1 < limit2) {
                return 1;
            } else if (limit1 > limit2) {
                return -1;
            } else {
                if (rate1 > rate2) {
                    return 1;
                } else if (rate1 < rate2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }
}
