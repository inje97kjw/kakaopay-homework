package com.kakaopay.support.bank.service.format;

import com.kakaopay.support.bank.entity.BankSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicFormat implements CustomFormat {
    private String region;
    private String target;
    private String usage;
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;

    @Override
    public BasicFormat generateFormat(BankSupport bankSupport) {
        return BasicFormat.builder()
                .region(bankSupport.getRegionCode().getName())
                .target(bankSupport.getTarget())
                .usage(bankSupport.getUsage())
                .limit(bankSupport.getLimit())
                .rate(bankSupport.getRate())
                .institute(bankSupport.getInstitute())
                .mgmt(bankSupport.getMgmt())
                .reception(bankSupport.getReception())
                .build();
    }

}
