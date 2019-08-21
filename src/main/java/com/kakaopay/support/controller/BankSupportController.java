package com.kakaopay.support.controller;

import com.kakaopay.support.model.ApiResult;
import com.kakaopay.support.model.UpdateBankSupport;
import com.kakaopay.support.model.search.RequestBankSupport;
import com.kakaopay.support.model.search.ResponceBankSupport;
import com.kakaopay.support.service.BankSupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank-support")
public class BankSupportController {

    private BankSupportService bankSupportService;

    public BankSupportController(BankSupportService  bankSupportService) {
        this.bankSupportService = bankSupportService;
    }

    @PostMapping("/upload-support-data")
    public ApiResult uploadData(@RequestParam("file") MultipartFile file) {
        String result = bankSupportService.saveUploadData(file);
        return ApiResult.builder()
                .status(HttpStatus.OK.value())
                .message(result)
                .build();
    }

    @GetMapping("/list")
    public List<ResponceBankSupport> getSupportInfoList() {
        return bankSupportService.getBankSupportListAll();
    }

    @GetMapping("/support")
    public ResponceBankSupport getSupportInfo(@RequestBody(required=true) RequestBankSupport requestBankSupport) {
        return bankSupportService.getBankSupport(requestBankSupport);
    }

    @PutMapping("/support/{regionCode}")
    public ApiResult updateSupportInfo(@PathVariable String regionCode, @RequestBody(required=true) UpdateBankSupport updateBankSupport) {
        String result = bankSupportService.updateBankSupport(regionCode, updateBankSupport);
        return ApiResult.builder()
                .status(HttpStatus.OK.value())
                .message(result)
                .build();
    }
}
