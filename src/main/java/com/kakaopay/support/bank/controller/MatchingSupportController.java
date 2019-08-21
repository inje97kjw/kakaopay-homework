package com.kakaopay.support.bank.controller;

import com.kakaopay.support.bank.model.search.RequestBankSupport;
import com.kakaopay.support.bank.model.search.ResponceBankSupport;
import com.kakaopay.support.bank.model.search.Sort;
import com.kakaopay.support.bank.service.BankSupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list/max-limit-support")
    public List<String> getMaxLimitSupportList(@RequestBody(required=true) RequestBankSupport requestBankSupport) {
        requestBankSupport.setSort(Sort.LIMIT_DESC);

        List<ResponceBankSupport> responceBankSupportList = bankSupportService.getBankSupportList(requestBankSupport);
        return responceBankSupportList.stream()
                .map(ResponceBankSupport::getRegion)
                .collect(Collectors.toList());
    }

    @GetMapping("/list/min-rate-support")
    public List<String> getMinRateSupportList() {
        RequestBankSupport requestBankSupport = RequestBankSupport.builder()
                .count(1)
                .sort(Sort.RATE_ASC)
                .build();

        List<ResponceBankSupport> responceBankSupportList = bankSupportService.getBankSupportList(requestBankSupport);
        return responceBankSupportList.stream()
                .map(ResponceBankSupport::getRegion)
                .collect(Collectors.toList());
    }
}
