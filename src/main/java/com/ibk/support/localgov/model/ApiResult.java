package com.ibk.support.localgov.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResult {
    int status;
    String message;

}
