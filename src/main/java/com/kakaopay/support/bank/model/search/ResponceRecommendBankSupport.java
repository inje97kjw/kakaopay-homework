package com.kakaopay.support.bank.model.search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponceRecommendBankSupport {
    private String region;
    private String usage;
    private String limit;
    private String rate;
}
