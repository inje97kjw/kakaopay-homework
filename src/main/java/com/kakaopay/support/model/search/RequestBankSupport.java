package com.kakaopay.support.model.search;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Builder
public class RequestBankSupport {
    String region;
    int count;
    Sort sort;

    public boolean isResionCondition () {
        return !StringUtils.isEmpty(this.region);
    }
}
