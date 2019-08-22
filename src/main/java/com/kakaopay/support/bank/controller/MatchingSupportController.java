package com.kakaopay.support.bank.controller;

import com.kakaopay.support.bank.model.search.Format;
import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.service.BankSupportService;
import com.kakaopay.support.bank.service.format.BasicFormat;
import com.kakaopay.support.bank.service.format.CustomFormat;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/matching-support")
public class MatchingSupportController {

    private BankSupportService bankSupportService;

    public MatchingSupportController(BankSupportService  bankSupportService) {
        this.bankSupportService = bankSupportService;
    }

    @ApiOperation(value = "지원한도 컬럼에서 지원금액으로 내림차순 정렬하여 특정 개수만 출력하는 API 개발")
    @GetMapping("/max-limit-support")
    public List<String> getMaxLimitSupportList(@RequestBody(required=true) RequestBankSupport requestBankSupport) {
        requestBankSupport.setSort(Sort.LIMIT);
        requestBankSupport.setFormat(Format.BASIC);

        List<CustomFormat> responceBankSupportList = bankSupportService.getBankSupportList(requestBankSupport);
        return responceBankSupportList.stream()
                .map(e -> ((BasicFormat)e).getRegion())
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API")
    @GetMapping("/min-rate-support")
    public List<String> getMinRateSupportList() {
        RequestBankSupport requestBankSupport = RequestBankSupport.builder()
                .count(1)
                .sort(Sort.RATE)
                .format(Format.BASIC)
                .build();

        List<CustomFormat> responceBankSupportList = bankSupportService.getBankSupportList(requestBankSupport);
        return responceBankSupportList.stream()
                .map(e -> ((BasicFormat)e).getRegion())
                .collect(Collectors.toList());
    }
}
