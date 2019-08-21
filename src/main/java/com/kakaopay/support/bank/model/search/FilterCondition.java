package com.kakaopay.support.bank.model.search;

import com.kakaopay.support.bank.entity.BankSupport;
import com.kakaopay.support.bank.util.ConvertUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class FilterCondition {
    private String region;
    private String usageDriver;
    private String usageFacilities;
    private String limit;
    private String rate;

    public List<BankSupport> excutefilter(List<BankSupport> bankSupportList) {
        return bankSupportList.stream()
                .filter(e -> usageFilter(e))
                .filter(e -> rateFilter(e))
                .filter(e -> limitFilter(e))
                .collect(Collectors.toList());
    }

    private boolean rateFilter (BankSupport bankSupport) {
        Rate rate1 = ConvertUtil.rateConvertToNumber(bankSupport.getRate());
        Rate rate2 = ConvertUtil.rateConvertToNumber(rate);
        if (rate1.getMax() >= rate2.getMax()) {
            return true;
        }
        return false;
    }

    private boolean limitFilter (BankSupport bankSupport) {
        long limit1 = ConvertUtil.limitConvertToNumber(bankSupport.getLimit());
        long limit2 = ConvertUtil.limitConvertToNumber(limit);
        if (limit1 >= limit2) {
            return true;
        }
        return false;
    }

    private boolean usageFilter (BankSupport bankSupport) {
        if (bankSupport.getUsage().equals(usageCondition())) {
            return true;
        }
        return false;
    }

    private String usageCondition () {
        String usage = "";
        if (!usage.isEmpty() && !usageFacilities.isEmpty()) {
            usage = usageDriver + "및" + usageFacilities;
        } else {
            usage += usageDriver;
            usage += usageFacilities;
        }
        return usage;
    }
}
