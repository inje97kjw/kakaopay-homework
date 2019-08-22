package com.kakaopay.support.bank.model.search;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Builder
public class RequestBankSupport {
    String region;
    int count;
    Sort sort;
    Format format;

    public boolean isResionCondition () {
        return !StringUtils.isEmpty(this.region);
    }

    public int adjustLimitCount(int listSize) {
        if (listSize < this.count) {
            this.count = listSize;
        }
        return this.count;
    }
}
