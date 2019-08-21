package com.ibk.support.localgov.model.search;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SearchRequest {
    String region;
    int count = Integer.MAX_VALUE;
    Sort sort = Sort.DEFAULT;

    public boolean isResionCondition () {
        return !StringUtils.isEmpty(this.region);
    }
}
