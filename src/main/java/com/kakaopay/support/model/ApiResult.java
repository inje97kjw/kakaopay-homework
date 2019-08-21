package com.kakaopay.support.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResult {
    int status;
    String message;

}
