package com.kakaopay.support.model.search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponceBankSupport {
    private String region;
    private String target;
    private String usage;
    private String limit;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;
}
